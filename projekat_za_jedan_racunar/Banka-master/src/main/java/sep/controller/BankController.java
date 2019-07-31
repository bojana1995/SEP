package sep.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sep.dto.CardDTO;
import sep.dto.MerchantDTO;
import sep.dto.URLDTO;
import sep.model.Card;
import sep.model.PaymentUrlAndId;
import sep.model.Request;
import sep.service.CardService;
import sep.service.PaymentUrlAndIdService;
import sep.service.RequestService;

@RestController
@RequestMapping(value = "/banka")
public class BankController {

	@Autowired
	RequestService requestService;

	@Autowired
	PaymentUrlAndIdService paymentService;

	@Autowired
	CardService cardService;
	
	//@Autowired
	//RestTemplate resttemplate;

	private static final Logger logger = LoggerFactory.getLogger(BankController.class);
	
	@CrossOrigin
	@RequestMapping(value = "/startPayment")
	public String proveriZahtev(@RequestBody MerchantDTO merchant) {
		merchant.setSuccessUrl("http://localhost:8762/banka-service/uspesno.html");
		merchant.setErrorUrl("http://localhost:8762/banka-service/greska.html");
		merchant.setFailedUrl("http://localhost:8762/banka-service/failed.html");
		System.out.println("\n\t\tDOSAO U BANKU\n");
		boolean isMerchantOk = merchantHasCard(merchant);
		System.out.println("\n\t\tProvera da li je merchant ok: " + isMerchantOk + "\n");

		if (isMerchantOk) {
			PaymentUrlAndId toSave = new PaymentUrlAndId();
			toSave.setPaymentUrl("http://localhost:8762/banka-service/banka.html?paymentId=");
			PaymentUrlAndId saved = paymentService.save(toSave);
			saveRequest(merchant, saved.getId());
			logger.info("\n\t\tMerchant je prošao proveru, moguće je nastaviti proces.\n");
			return saved.getPaymentUrl()+saved.getId();
		} else {
			logger.info("\n\t\tMerchant nije prošao proveru.\n");
			return merchant.getErrorUrl();
		}
	}
	

	@RequestMapping(value = "/postaviMerchanta")
	public MerchantDTO postaviMerchanta(@RequestBody CardDTO cardDTO) {
		Card card = cardService.getCardByPanAndSecurityCode(new Long(cardDTO.getPan()), new Long(cardDTO.getSecurityCode()));
		MerchantDTO merchantDTO = new MerchantDTO();
		merchantDTO.setMerchant_id(card.getMerchantId().toString());
		merchantDTO.setMerchant_password("pass");
		return merchantDTO;
	}

	public void saveRequest(MerchantDTO merchant, Long paymentId) {
		Request toSave = new Request();
		toSave.setPaymentId(paymentId);
		toSave.setAmount(merchant.getAmount());
		toSave.setMerchantId(merchant.getMerchant_id());
		toSave.setMerchantOrderId(merchant.getMerchant_order_id());
		toSave.setMerchantPassword(merchant.getMerchant_password());
		toSave.setMerchantTimestamp(merchant.getMerchant_timestamp());
		toSave.setErrorUrl(merchant.getErrorUrl());
		toSave.setFailedUrl(merchant.getFailedUrl());
		toSave.setSuccessUrl(merchant.getSuccessUrl());
		requestService.save(toSave);
		return;
	}

	public Boolean merchantHasCard(MerchantDTO merchant) {
		List<Card> kartice = cardService.getAll();
		Long merchantId = new Long(merchant.getMerchant_id());
		for (Card card : kartice) {
			if (card.getMerchantId().compareTo(merchantId) == 0) {
				return true;
			}
		}
		return false;
	}

	@CrossOrigin
	@RequestMapping(value = "/kupiCasopis", method = RequestMethod.POST)
	public ResponseEntity<?> kupiCasopis(@RequestBody CardDTO cardDTO) {
		URLDTO urlDTO = new URLDTO();
		RestTemplate client1 = new RestTemplate();
		HttpHeaders headers1 = new HttpHeaders();
		CardDTO karticaCasopisa = findCard(cardDTO);

		String kupacBanka = cardDTO.getPan().substring(1, 7);
		String casopisBanka = karticaCasopisa.getPan().substring(1, 7);

		if (kupacBanka.equals(casopisBanka)) {
			urlDTO = izvrsiPlacanje(cardDTO);
			return new ResponseEntity<>(urlDTO, HttpStatus.OK);
		} else {
			HttpHeaders headers = new HttpHeaders();
			RestTemplate client = new RestTemplate();
			try {
				cardDTO.setAcquirerId((int) (Math.random() * 1000000000));
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				cardDTO.setAcquirerTimestamp(ts);
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<CardDTO> entity = new HttpEntity<>(cardDTO, headers);
				urlDTO = client.postForObject("http://localhost:1237/platneKartice/usmeriNaBanku", entity,
						URLDTO.class);
				return new ResponseEntity<>(urlDTO, HttpStatus.OK);
			} catch (Exception e) {
				System.out.println("\n\t\tNe moze da usmeri zahtev na banku.\n\n");
				return null;
			}
		}
	}

	@CrossOrigin
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/izvrsiPlacanje", method = RequestMethod.POST)
	public URLDTO izvrsiPlacanje(@RequestBody CardDTO card) {
		List<Card> listaKartica = cardService.getAll();
		Long pan = new Long(card.getPan());
		Long securityCode = new Long(card.getSecurityCode());
		Boolean postojiKartica = false;
		Card karticaPlacenika = new Card();
		Card karticaMarchanta = new Card();
		Long paymentId = new Long(card.getPaymentId());
		List<Request> requestList = requestService.getAll();
		Request trazeni = new Request();

		for (Request req : requestList) {
			if (req.getPaymentId().compareTo(paymentId) == 0) {
				trazeni = req;
			}
		}

		Long idMerchanta = new Long(trazeni.getMerchantId());
		for (Card c : listaKartica) {
			if (card.getCardHolderName().equals(c.getCardHolderName())
					&& card.getExpirationDate().equals(c.getExpirationDate()) && pan.compareTo(c.getPan()) == 0
					&& securityCode.compareTo(c.getSecurityCode()) == 0) {
				System.out.println("\n\t\tPostoji čovek sa ovom platnom karticom.\n");
				postojiKartica = true;
				karticaPlacenika = c;
			}
			if (c.getMerchantId() != null && c.getMerchantId().compareTo(idMerchanta) == 0) {
				karticaMarchanta = c;
			}
		}

		if (postojiKartica) {
			if (trazeni.getId() != null) {
				if (karticaPlacenika.getAmount().compareTo(trazeni.getAmount()) > 0) {
					karticaPlacenika.setAmount(karticaPlacenika.getAmount().subtract(trazeni.getAmount()));
					Card nakonTransakcije = cardService.save(karticaPlacenika);
					System.out.println("\n\t\tIznos nakon transkacije na plaćenikovoj kartici: "
							+ nakonTransakcije.getAmount() + "\n");
					karticaMarchanta.setAmount(karticaMarchanta.getAmount().add(trazeni.getAmount()));
					Card nakonTransk2 = cardService.save(karticaMarchanta);
					System.out.println("\n\t\tIznos nakon transkacije na merchant-ovoj kartici: "
							+ nakonTransk2.getAmount() + "\n");
					URLDTO url = new URLDTO();
					url.setAcquirerId(card.getAcquirerId());
					url.setAcquirerTimestamp(card.getAcquirerTimestamp());
					Integer payId = Math.toIntExact(trazeni.getPaymentId());
					url.setPaymentId(payId);
					url.setMerchant_order_id(trazeni.getMerchantOrderId());
					url.setUrl("http://localhost:8762/banka-service/uspesno.html");
					logger.info("Izvršeno plaćanje: " + url + "\n");
					return url;
				}
			}
		}
		URLDTO url = new URLDTO();
		url.setAcquirerId(card.getAcquirerId());
		url.setAcquirerTimestamp(card.getAcquirerTimestamp());
		Integer payId = Math.toIntExact(trazeni.getPaymentId());
		url.setPaymentId(payId);
		url.setMerchant_order_id(trazeni.getMerchantOrderId());
		url.setUrl("https://localhost:9081/failed.html");
		logger.info("Transakcija nije uspešna!");
		return url;
	}

	public CardDTO findCard(@RequestBody CardDTO card) {
		Long paymentId = new Long(card.getPaymentId());
		List<Request> requestList = requestService.getAll();
		Request trazeni = new Request();

		for (Request req : requestList) {
			if (req.getPaymentId().compareTo(paymentId) == 0) {
				trazeni = req;
			}
		}

		List<Card> listaKartica = cardService.getAll();
		Card karticaMarchanta = new Card();
		Long idMerchanta = new Long(trazeni.getMerchantId());

		for (Card c : listaKartica) {
			if (c.getMerchantId() != null && c.getMerchantId().compareTo(idMerchanta) == 0) {
				karticaMarchanta = c;
			}
		}

		CardDTO cardToReturn = new CardDTO();
		cardToReturn.setPan(karticaMarchanta.getPan().toString());

		logger.info("\n\t\tVlasnik kartice: " + cardToReturn.getCardHolderName() + "\n");
		return cardToReturn;
	}

}
