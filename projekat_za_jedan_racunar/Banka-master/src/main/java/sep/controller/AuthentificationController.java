package sep.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sep.dto.CardDTO;
import sep.dto.URLDTO;
import sep.model.Card;
import sep.model.PaymentUrlAndId;
import sep.model.Request;
import sep.service.CardService;
import sep.service.PaymentUrlAndIdService;
import sep.service.RequestService;

@RestController
@RequestMapping(value = "/payment")
public class AuthentificationController {

	@Autowired
	private CardService cardService;

	@Autowired
	private RequestService requestService;

	private static final Logger logger = LoggerFactory.getLogger(AuthentificationController.class);

	@CrossOrigin
	@RequestMapping(value = "/autentifikacijaUsera", method = RequestMethod.POST)
	public CardDTO autentifikacijaUsera(@RequestBody CardDTO card) {
		List<Card> listaKartica = cardService.getAll();
		Long pan = new Long(card.getPan());
		Long securityCode = new Long(card.getSecurityCode());
		Boolean postojiKartica = false;
		Card karticaPlacenika = new Card();
		Long paymentId = new Long(card.getPaymentId());
		List<Request> requestList = requestService.getAll();
		Request trazeni = new Request();

		for (Request req : requestList) {
			if (req.getPaymentId().compareTo(paymentId) == 0) {
				trazeni = req;
			}
		}

		for (Card c : listaKartica) {
			if (card.getCardHolderName().equals(c.getCardHolderName())
					&& card.getExpirationDate().equals(c.getExpirationDate()) && pan.compareTo(c.getPan()) == 0
					&& securityCode.compareTo(c.getSecurityCode()) == 0) {
				System.out.println("\n\t\tPostoji čovek sa ovom platnom karticom.\n");
				postojiKartica = true;
				karticaPlacenika = c;
			}
		}

		if (postojiKartica) {
			if (trazeni.getId() != null) {
				if (karticaPlacenika.getAmount().compareTo(trazeni.getAmount()) > 0) {
					card.setIssuerId((int) (Math.random() * 1000000000));
					Date date = new Date();
					Timestamp ts = new Timestamp(date.getTime());
					card.setIssuerTimestamp(ts);
					return card;
				}
			}
		}
		return null;
	}

}
