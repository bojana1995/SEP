package udd_upp.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import udd_upp.Kriptovanje;
import udd_upp.anotacije.PermisijaAnotacija;
import udd_upp.dto.CardDTO;
import udd_upp.dto.MerchantDTO;
import udd_upp.dto.URLDTO;
import udd_upp.model.Casopis;
import udd_upp.model.Korisnik;
import udd_upp.model.NaucnaOblast;
import udd_upp.model.Rad;
import udd_upp.model.TipCasopisa;
import udd_upp.model.TipPlacanja;
import udd_upp.repository.CasopisRepository;
import udd_upp.service.CasopisService;
import udd_upp.service.KorisnikService;

@RestController
@RequestMapping(value = "/casopis")
public class CasopisController {

	@Autowired
	CasopisService casopisService;
	
	@Autowired
	CasopisRepository casopisRepository;
	
	@Autowired
	KorisnikService korisnikService;
	
	private static final Logger logger = LoggerFactory.getLogger(CasopisController.class);
	
	//preuzimanje casopisa sa zadatim id-em
	//@PermisijaAnotacija(naziv = "GET_CASOPIS")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Casopis> getCasopis(@PathVariable Long id) {
		Casopis casopis = casopisService.findOne(id);
			
		if (casopis == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(casopis, HttpStatus.OK);
	}
		
	//preuzimanje svih casopisa
	//@PermisijaAnotacija(naziv = "GET_CASOPISI")
	@RequestMapping(value="/getCasopisi", method = RequestMethod.GET)
	public ResponseEntity<List<Casopis>> getCasopisi() {
		List<Casopis> casopisi = casopisService.findAll();
		
		if(casopisi.equals(null)) {
			return new ResponseEntity<>(casopisi, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(casopisi, HttpStatus.OK);
	}
	
	//dodavanje casopisa
	//@PermisijaAnotacija(naziv = "DODAJ_CASOPIS")
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Casopis> dodaj(@RequestBody Casopis requestCasopis) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {		
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		if(requestCasopis.getSigurnosniKod()!=null && !requestCasopis.getSigurnosniKod().equals("")) {
			CardDTO cardDTO = new CardDTO();
			cardDTO.setSecurityCode(requestCasopis.getSigurnosniKod());
			cardDTO.setPan(requestCasopis.getPan());
			RestTemplate client = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CardDTO> entity = new HttpEntity<>(cardDTO, headers);
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO = client.postForObject("http://192.168.43.218:1235/banka/postaviMerchanta", entity,
					MerchantDTO.class);
			requestCasopis.setMerchantId(merchantDTO.getMerchant_id());
			requestCasopis.setMerchantPassword(merchantDTO.getMerchant_password());
		}
		
		//String encryptedMerchantId = Kriptovanje.encrypt(requestCasopis.getMerchantId());
		String encryptedMerchantPassword = Kriptovanje.encrypt(requestCasopis.getMerchantPassword());
		
		Casopis c = new Casopis(requestCasopis.getNaziv(), requestCasopis.getISSNBroj(), 
				requestCasopis.getTipCasopisa(), requestCasopis.getTipoviPlacanja(),
				requestCasopis.getAmount(), requestCasopis.getMerchantId(), 
				encryptedMerchantPassword, requestCasopis.getNaucneOblasti(), requestCasopis.getPaypalId(),
				requestCasopis.getPaypalSecret()); 
		
		if(!c.getNaziv().isEmpty() && !c.getMerchantId().isEmpty() && !c.getMerchantPassword().isEmpty()) {
			if(requestCasopis.isClanarinaSeNaplacujeCitaocu()) {
				c.setClanarinaSeNaplacujeCitaocu(true);
				c.setTipCasopisa(TipCasopisa.SA_PRETPLATOM);
			}
			else {
				c.setClanarinaSeNaplacujeCitaocu(false);
				c.setTipCasopisa(TipCasopisa.OPEN_ACCESS);
			}
			if(c.getTipoviPlacanja().isEmpty()) {
				ArrayList<TipPlacanja> tipoviPlacanja = new ArrayList<TipPlacanja>();
				TipPlacanja inicijalniTipPlacanja = TipPlacanja.BANKA; //inicijalno, ako korisnik nista ne odabere
				tipoviPlacanja.add(inicijalniTipPlacanja);
				c.setTipoviPlacanja(tipoviPlacanja);
			} else
				c.setTipoviPlacanja(requestCasopis.getTipoviPlacanja());
			c.setNaucneOblasti(new HashSet<NaucnaOblast>());
			c.setRecenzenti(new HashSet<Korisnik>());
			c.setUrednici(new HashSet<Korisnik>());
			casopisService.save(c);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno dodao časopis " + c.getNaziv() + ".\n");
			return new ResponseEntity<Casopis>(c, HttpStatus.OK);
		}
			
		logger.info("\n\t\tNeuspešan pokušaj dodavanja časopisa.\n");
		return new ResponseEntity<Casopis>(c, HttpStatus.BAD_REQUEST);
	}
	
	//azuriranje casopisa
	@PermisijaAnotacija(naziv = "AZURIRAJ_CASOPIS")
	@RequestMapping(value="/azuriraj/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Casopis> azuriraj(@PathVariable Long id, @RequestBody Casopis requestCasopis) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		
		Casopis c = casopisService.findOne(id);
		
		if(c != null) {
			c.setNaziv(requestCasopis.getNaziv());
			c.setISSNBroj(requestCasopis.getISSNBroj());
			c.setTipCasopisa(requestCasopis.getTipCasopisa());
			c.setClanarinaSeNaplacujeCitaocu(requestCasopis.isClanarinaSeNaplacujeCitaocu());
			if(c.isClanarinaSeNaplacujeCitaocu()) {
				c.setClanarinaSeNaplacujeCitaocu(true);
				c.setTipCasopisa(TipCasopisa.SA_PRETPLATOM);
			} else {
				c.setClanarinaSeNaplacujeCitaocu(false);
				c.setTipCasopisa(TipCasopisa.OPEN_ACCESS);
			}
			if(c.getTipoviPlacanja().isEmpty()) {
				ArrayList<TipPlacanja> tipoviPlacanja = new ArrayList<TipPlacanja>();
				TipPlacanja inicijalniTipPlacanja = TipPlacanja.BANKA; //inicijalno, ako korisnik nista ne odabere
				tipoviPlacanja.add(inicijalniTipPlacanja);
				c.setTipoviPlacanja(tipoviPlacanja);
			} else
				c.setTipoviPlacanja(requestCasopis.getTipoviPlacanja());
			c.setAmount(requestCasopis.getAmount());
			c.setMerchantId(requestCasopis.getMerchantId());
			c.setMerchantPassword(requestCasopis.getMerchantPassword());
			
			casopisService.save(c);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno ažurirao naučni časopis " + c.getNaziv() + ".\n");
			return new ResponseEntity<Casopis>(c, HttpStatus.OK);
		}
		
		logger.info("\n\t\tNeuspešan pokušaj ažuriranja naučnog časopisa " + c.getNaziv() + ".\n");
		return new ResponseEntity<Casopis>(c, HttpStatus.NOT_FOUND);
	}
	
	//brisanje casopisa
	@PermisijaAnotacija(naziv = "OBRISI_CASOPIS")
	@RequestMapping(value="/obrisi/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Casopis> obrisi(@PathVariable Long id) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());	
		
		Casopis c = casopisService.findOne(id);
			
		if(c != null) {
			casopisService.delete(c.getId());
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno obrisao naučni časopis " + c.getNaziv() + ".\n");
			return new ResponseEntity<Casopis>(c, HttpStatus.OK);
		}
			
		logger.info("\n\t\tNeuspešan pokušaj brisanja naučnog časopisa " + c.getNaziv() + ".\n");
		return new ResponseEntity<Casopis>(c, HttpStatus.NOT_FOUND);
	}
	
	
	
	//SEP - deo za placanje
	//@PermisijaAnotacija(naziv = "PROVERI_MERCHANTA")
	@RequestMapping(value = "/proveriMerchanta", method = RequestMethod.POST)
	public boolean proveriMerchanta(@RequestBody MerchantDTO casopis) {
		List<Casopis> casopisi = casopisService.findAll();

		for (Casopis c : casopisi) {
			if (c.getMerchantId().equals(casopis.getMerchant_id()) && c.getMerchantPassword().equals(casopis.getMerchant_password())) {
				return true;
			}
		}
		
		return false;
	}

	
	
}
