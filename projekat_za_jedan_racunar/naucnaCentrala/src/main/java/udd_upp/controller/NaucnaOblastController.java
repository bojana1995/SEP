package udd_upp.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
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
import udd_upp.model.Casopis;
import udd_upp.model.Korisnik;
import udd_upp.model.NaucnaOblast;
import udd_upp.repository.NaucnaOblastRepository;
import udd_upp.service.KorisnikService;
import udd_upp.service.NaucnaOblastService;

@RestController
@RequestMapping(value = "/naucnaOblast")
public class NaucnaOblastController {

	@Autowired
	NaucnaOblastService naucnaOblastService;
	
	@Autowired
	NaucnaOblastRepository naucnaOblastRepository;
	
	@Autowired
	KorisnikService korisnikService;
	
	private static final Logger logger = LoggerFactory.getLogger(NaucnaOblastController.class);
	
	//preuzimanje naucne oblasti sa zadatim id-em
	//@PermisijaAnotacija(naziv = "GET_NAUCNA_OBLAST")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<NaucnaOblast> getNaucnaOblast(@PathVariable Long id) {
		NaucnaOblast naucnaOblast = naucnaOblastService.findOne(id);
				
		if (naucnaOblast == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(naucnaOblast, HttpStatus.OK);
	}
			
	//preuzimanje svih naucnih oblasti
	//@PermisijaAnotacija(naziv = "GET_NAUCNE_OBLASTI")
	@RequestMapping(value="/getNaucneOblasti", method = RequestMethod.GET)
	public ResponseEntity<List<NaucnaOblast>> getNaucneOblasti() {
		List<NaucnaOblast> naucneOblasti = naucnaOblastService.findAll();
				
		if(naucneOblasti.equals(null)) {
			return new ResponseEntity<>(naucneOblasti, HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(naucneOblasti, HttpStatus.OK);
	}
	
	//dodavanje naucne oblasti
	@PermisijaAnotacija(naziv = "DODAJ_NAUCNU_OBLAST")
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NaucnaOblast> dodaj(@RequestBody NaucnaOblast requestNaucnaOblast) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {		
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		
		NaucnaOblast no = new NaucnaOblast(requestNaucnaOblast.getNaziv(), requestNaucnaOblast.getOpis());
				
		if(!no.getNaziv().isEmpty() && !no.getOpis().isEmpty()) {
			no.setCasopisi(new HashSet<Casopis>());
			no.setUredniciRecenzenti(new HashSet<Korisnik>());
			naucnaOblastService.save(no);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno dodao naučnu oblast " + no.getNaziv() + ".\n");
			return new ResponseEntity<NaucnaOblast>(no, HttpStatus.OK);
		}
				
		logger.info("\n\t\tNeuspešan pokušaj dodavanja naučne oblasti.\n");
		return new ResponseEntity<NaucnaOblast>(no, HttpStatus.BAD_REQUEST);
	}
	
	//azuriranje naucne oblasti
	@PermisijaAnotacija(naziv = "AZURIRAJ_NAUCNU_OBLAST")
	@RequestMapping(value="/azuriraj/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NaucnaOblast> azuriraj(@PathVariable Long id, @RequestBody NaucnaOblast requestNaucnaOblast) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());	
		
		NaucnaOblast no = naucnaOblastService.findOne(id);
			
		if(no != null) {
			no.setNaziv(requestNaucnaOblast.getNaziv());
			no.setOpis(requestNaucnaOblast.getOpis());
				
			naucnaOblastService.save(no);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno ažurirao naučnu oblast " + no.getNaziv() + ".\n");
			return new ResponseEntity<NaucnaOblast>(no, HttpStatus.OK);
		}
			
		logger.info("\n\t\tNeuspešan pokušaj ažuriranja naučne oblasti " + no.getNaziv() + ".\n");
		return new ResponseEntity<NaucnaOblast>(no, HttpStatus.NOT_FOUND);
	}
		
	//brisanje naucne oblasti
	@PermisijaAnotacija(naziv = "OBRISI_NAUCNU_OBLAST")
	@RequestMapping(value="/obrisi/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NaucnaOblast> obrisi(@PathVariable Long id) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());	
		
		NaucnaOblast no = naucnaOblastService.findOne(id);
				
		if(no != null) {
			//kaskadno brisanje
			if(!k.getNaucneOblastiKojePokriva().isEmpty())
				if(k.getRola().getId().equals(3L) || k.getRola().getId().equals(4L)) //urednik ili recenzent
					for(int i = 0; i < k.getNaucneOblastiKojePokriva().size(); i++)
						if(k.getNaucneOblastiKojePokriva().get(i).getId().equals(no.getId()))
							k.getNaucneOblastiKojePokriva().remove(i);
			
			naucnaOblastService.delete(no.getId());
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno obrisao naučnu oblast " + no.getNaziv() + ".\n");
			return new ResponseEntity<NaucnaOblast>(no, HttpStatus.OK);
		}
				
		logger.info("\n\t\tNeuspešan pokušaj brisanja naučne oblasti " + no.getNaziv() + ".\n");
		return new ResponseEntity<NaucnaOblast>(no, HttpStatus.NOT_FOUND);
	}
	
}
