package udd_upp.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import udd_upp.dto.RadDTO;
import udd_upp.model.Casopis;
import udd_upp.model.Korisnik;
import udd_upp.model.NaucnaOblast;
import udd_upp.model.Rad;
import udd_upp.model.TipCasopisa;
import udd_upp.repository.RadRepository;
import udd_upp.service.CasopisService;
import udd_upp.service.KorisnikService;
import udd_upp.service.NaucnaOblastService;
import udd_upp.service.RadService;

@RestController
@RequestMapping(value = "/rad")
public class RadController {

	@Autowired
	RadService radService;
	
	@Autowired
	RadRepository radRepository;
	
	@Autowired
	KorisnikService korisnikService;
	
	@Autowired 
	CasopisService casopisService;
	
	@Autowired
	NaucnaOblastService naucnaOblastService;
	
	private static final Logger logger = LoggerFactory.getLogger(RadController.class);
	
	//preuzimanje rada sa zadatim id-em
	//@PermisijaAnotacija(naziv = "GET_RAD")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Rad> getRad(@PathVariable Long id) {
		Rad rad = radService.findOne(id);
				
		if (rad == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(rad, HttpStatus.OK);
	}
			
	//preuzimanje svih radova
	//@PermisijaAnotacija(naziv = "GET_RADOVI")
	@RequestMapping(value="/getRadovi", method = RequestMethod.GET)
	public ResponseEntity<List<RadDTO>> getRadovi() {
		List<Rad> radovi = radService.findAll();
		List<RadDTO> radoviDTO = new ArrayList<RadDTO>();
		for(Rad r : radovi) {
			RadDTO radDto = new RadDTO();
			radDto.setId(r.getId());
			radDto.setAmount(r.getAmount());
			radDto.setApstrakt(r.getApstrakt());
			radDto.setKljucneReci(r.getKljucniPojmovi());
			radDto.setAutor(r.getAutor().getIme() + " " + r.getAutor().getPrezime());
			radDto.setNaslovRada(r.getNaslov());
			radDto.setNaucnaOblast(r.getNaucnaOblast().getNaziv());
			radDto.setCasopisRada(r.getCasopis().getNaziv());
			radDto.setTipPlacanja(r.getCasopis().getTipoviPlacanja().toString());
			if(r.getCasopis().getTipCasopisa().equals(TipCasopisa.OPEN_ACCESS)) {
				radDto.setIsOpenAccess(true);
			}else {
				radDto.setIsOpenAccess(false);
			}
			radoviDTO.add(radDto);
		}
		
		if(radovi.equals(null)) {
			return new ResponseEntity<>(radoviDTO, HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(radoviDTO, HttpStatus.OK);
	}
	
	//dodavanje rada
	@PermisijaAnotacija(naziv = "DODAJ_RAD")
	@RequestMapping(value = "/dodajRad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void dodaj(@RequestBody RadDTO requestRad) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {		
		Korisnik k = korisnikService.getCurrentUser();	
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		
		Rad zaDodavanje = new Rad();
		Casopis casopisRada = casopisService.findOne(new Long(requestRad.getCasopisRada()));
		NaucnaOblast naucnaOblast = naucnaOblastService.findOne(new Long(requestRad.getNaucnaOblast()));
		
		if(!requestRad.getNaslovRada().isEmpty() && !requestRad.getKljucneReci().isEmpty() && !requestRad.getApstrakt().isEmpty()) {
			zaDodavanje.setAutor(k);
			zaDodavanje.setNaucnaOblast(naucnaOblast);
			zaDodavanje.setNaslov(requestRad.getNaslovRada());
			zaDodavanje.setApstrakt(requestRad.getApstrakt());
			zaDodavanje.setKljucniPojmovi(requestRad.getKljucneReci());
			zaDodavanje.setFajlName("dummy.pdf");
			zaDodavanje.setAmount(requestRad.getAmount());
			zaDodavanje.setCasopis(casopisRada);
			radService.save(zaDodavanje);
			//return new ResponseEntity<>(requestRad, HttpStatus.OK);
		}
			
		//return new ResponseEntity<>(requestRad, HttpStatus.BAD_REQUEST);
	}
			
	//azuriranje rada
	@PermisijaAnotacija(naziv = "AZURIRAJ_RAD")
	@RequestMapping(value="/azuriraj/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rad> azuriraj(@PathVariable Long id, @RequestBody Rad requestRad) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();	
		String decryptedString = Kriptovanje.decrypt(k.getEmail());	
		
		Rad r = radService.findOne(id);
				
		if(r != null) {
			r.setNaslov(requestRad.getNaslov());
			r.setKljucniPojmovi(requestRad.getKljucniPojmovi());
			r.setApstrakt(requestRad.getApstrakt());
			//r.setRadnaVerzijaPDF(requestRad.getRadnaVerzijaPDF());		
			//r.setFinalnaVerzijaPDF(requestRad.getFinalnaVerzijaPDF());
			//r.setNaucnaOblast(requestRad.getNaucnaOblast());
			
			radService.save(r);
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno ažurirao naučni rad " + r.getNaslov() + ".\n");
			return new ResponseEntity<Rad>(r, HttpStatus.OK);
		}
				
		logger.info("\n\t\tNeuspešan pokušaj ažuriranja naučnog rada " + r.getNaslov() + ".\n");
		return new ResponseEntity<Rad>(r, HttpStatus.NOT_FOUND);
	}
			
	//brisanje rada
	@PermisijaAnotacija(naziv = "OBRISI_RAD")
	@RequestMapping(value="/obrisi/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rad> obrisi(@PathVariable Long id) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();	
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		
		Rad r = radService.findOne(id);
					
		if(r != null) {
			radService.delete(r.getId());
			logger.info("\n\t\tKorisnik " + decryptedString + " je uspešno obrisao naučni rad " + r.getNaslov() + ".\n");
			return new ResponseEntity<Rad>(r, HttpStatus.OK);
		}
					
		logger.info("\n\t\tNeuspešan pokušaj brisanja naučnog rada " + r.getNaslov() + ".\n");
		return new ResponseEntity<Rad>(r, HttpStatus.NOT_FOUND);
	}
	
}
