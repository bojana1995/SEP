package udd_upp.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import udd_upp.Kriptovanje;
import udd_upp.anotacije.PermisijaAnotacija;
import udd_upp.model.Korisnik;
import udd_upp.model.Preporuka;
import udd_upp.model.Recenzija;
import udd_upp.repository.RecenzijaRepository;
import udd_upp.service.KorisnikService;
import udd_upp.service.RecenzijaService;

@RestController
@RequestMapping(value = "/recenzija")
public class RecenzijaController {

	@Autowired
	RecenzijaService recenzijaService;
	
	@Autowired
	RecenzijaRepository recenzijaRepository;
	
	@Autowired
	KorisnikService korisnikService;
	
	private static final Logger logger = LoggerFactory.getLogger(RecenzijaController.class);
	
	//preuzimanje recenzije sa zadatim id-em
	@PermisijaAnotacija(naziv = "GET_RECENZIJA")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Recenzija> getRecenzija(@PathVariable Long id) {
		Recenzija recenzija = recenzijaService.findOne(id);
				
		if (recenzija == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(recenzija, HttpStatus.OK);
	}
			
	//preuzimanje svih recenzija
	@PermisijaAnotacija(naziv = "GET_RECENZIJE")
	@RequestMapping(value="/getRecenzije", method = RequestMethod.GET)
	public ResponseEntity<List<Recenzija>> getRecenzije() {
		List<Recenzija> recenzije = recenzijaService.findAll();
				
		if(recenzije.equals(null)) {
			return new ResponseEntity<>(recenzije, HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(recenzije, HttpStatus.OK);
	}
	
	//dodavanje recenzije
	@PermisijaAnotacija(naziv = "DODAJ_RECENZIJU")
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Recenzija> dodaj(@RequestBody Recenzija requestRecenzija) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {		
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		
		Recenzija r = new Recenzija(requestRecenzija.getNaslov(), requestRecenzija.getKomentar(),requestRecenzija.getKomentarZaUrednike(), requestRecenzija.getDatum());
				
		if(!r.getNaslov().isEmpty() && !r.getKomentar().isEmpty() && !r.getDatum().toString().isEmpty()) {
			r.setVidljivaAutoru(true);
			r.setPreporuka(Preporuka.USLOVNO_PRIHVATITI_UZ_VECE_ISPRAVKE);
			recenzijaService.save(r);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno dodao recenziju " + r.getNaslov() + ".\n");
			return new ResponseEntity<Recenzija>(r, HttpStatus.OK);
		}
				
		logger.info("\n\t\tNeuspešan pokušaj dodavanja recenzije.\n");
		return new ResponseEntity<Recenzija>(r, HttpStatus.BAD_REQUEST);
	}
	
	//azuriranje recenzije
	@PermisijaAnotacija(naziv = "AZURIRAJ_RECENZIJU")
	@RequestMapping(value="/azuriraj/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Recenzija> azuriraj(@PathVariable Long id, @RequestBody Recenzija requestRecenzija) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		
		Recenzija r = recenzijaService.findOne(id);
					
		if(r != null) {
			r.setNaslov(requestRecenzija.getNaslov());
			r.setKomentar(requestRecenzija.getKomentar());
			r.setKomentarZaUrednike(requestRecenzija.getKomentarZaUrednike());	
			r.setDatum(requestRecenzija.getDatum());
			r.setPreporuka(requestRecenzija.getPreporuka());
			//r.setVidljivaAutoru(requestRecenzija.isVidljivaAutoru());
			
			recenzijaService.save(r);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno ažurirao recenziju " + r.getNaslov() + ".\n");
			return new ResponseEntity<Recenzija>(r, HttpStatus.OK);
		}
					
		logger.info("\n\t\tNeuspešan pokušaj ažuriranja recenzije " + r.getNaslov() + ".\n");
		return new ResponseEntity<Recenzija>(r, HttpStatus.NOT_FOUND);
	}
				
	//brisanje recenzije
	@PermisijaAnotacija(naziv = "OBRISI_RECENZIJU")
	@RequestMapping(value="/obrisi/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Recenzija> obrisi(@PathVariable Long id) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());	
		
		Recenzija r = recenzijaService.findOne(id);
						
		if(r != null) {
			recenzijaService.delete(r.getId());
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno obrisao recenziju " + r.getNaslov() + ".\n");
			return new ResponseEntity<Recenzija>(r, HttpStatus.OK);
		}
						
		logger.info("\n\t\tNeuspešan pokušaj brisanja recenzije " + r.getNaslov() + ".\n");
		return new ResponseEntity<Recenzija>(r, HttpStatus.NOT_FOUND);
	}
	
}
