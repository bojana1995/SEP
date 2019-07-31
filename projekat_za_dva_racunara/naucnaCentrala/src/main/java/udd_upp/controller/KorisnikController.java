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
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import udd_upp.model.Rad;
import udd_upp.repository.KorisnikRepository;
import udd_upp.service.EmailService;
import udd_upp.service.KorisnikService;
import udd_upp.service.NaucnaOblastService;
import udd_upp.service.RolaService;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikService korisnikService;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	RolaService rolaService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	NaucnaOblastService naucnaOblastService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(KorisnikController.class);
	
	//preuzimanje korisnika sa zadatim id-em
	//@PermisijaAnotacija(naziv = "GET_KORISNIK")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Korisnik> getKorisnik(@PathVariable Long id) {
		Korisnik korisnik = korisnikService.findOne(id);
				
		if (korisnik == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(korisnik, HttpStatus.OK);
	}
			
	//preuzimanje svih korisnika
	//@PermisijaAnotacija(naziv = "GET_KORISNICI")
	@RequestMapping(value="/getKorisnici", method = RequestMethod.GET)
	public ResponseEntity<List<Korisnik>> getKorisnici() {
		List<Korisnik> korisnici = korisnikService.findAll();
				
		if(korisnici.equals(null)) {
			return new ResponseEntity<>(korisnici, HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<>(korisnici, HttpStatus.OK);
	}
		
	//registracija korisnika
	//@PermisijaAnotacija(naziv = "REGISTRACIJA")
	@RequestMapping(value = "/registracija", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> registracija(@RequestBody Korisnik request) throws MailException, InterruptedException, MessagingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {		
		Korisnik k = new Korisnik(request.getEmail(), request.getLozinka(), request.getIme(), request.getPrezime(), request.getGrad(), request.getDrzava(), request.getTitula(), request.getNaucneOblastiKojePokriva()); 
		
		String encryptedString = Kriptovanje.encrypt(request.getEmail());
		k.setEmail(encryptedString);
		
		for(Korisnik kor : korisnikService.findAll()) {
			if(!kor.getEmail().equals(k.getEmail())) {
				if(!k.getEmail().isEmpty() && !k.getLozinka().isEmpty() && !k.getIme().isEmpty() && !k.getPrezime().isEmpty() && !k.getGrad().isEmpty() && !k.getDrzava().isEmpty() && k.getTitula().isEmpty()) {
					k.setRola(rolaService.findById(7L)); //citalac
					k.setNaucneOblastiKojePokriva(null);
					k.setCasopis(null);
					k.setCasopisi(null);
					k.setRadCijiSamAutor(null);
					emailService.sendMailAktivacijaKorisnickogNaloga(k);
					korisnikService.save(k);
					logger.info("\n\t\tKorisnik " + request.getEmail() + " se registrovao na sistem naučne centrale.\n");
					return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
				}else if(!k.getEmail().isEmpty() && !k.getLozinka().isEmpty() && !k.getIme().isEmpty() && !k.getPrezime().isEmpty() && !k.getGrad().isEmpty() && !k.getDrzava().isEmpty() && !k.getTitula().isEmpty()) {
					k.setAktiviranNalog(true);
					k.setRola(rolaService.findById(4L)); //recenzent, ima manje privilegije od urednika
					k.setNaucneOblastiKojePokriva(new ArrayList<NaucnaOblast>());
					k.setCasopisi(new HashSet<Casopis>());
					korisnikService.save(k);
					logger.info("\n\t\tKorisnik " + request.getEmail() + " se registrovao na sistem naučne centrale.\n");
					return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
				}
			}
		}
			
		logger.info("\n\t\tNeuspešan pokušaj registracije korisnika na sistem.\n");
		return new ResponseEntity<Korisnik>(k, HttpStatus.BAD_REQUEST);
	}
	
	//aktivacija korisnickog naloga posetom linka iz mejla
	//@PermisijaAnotacija(naziv = "AKTIVIRAJ_KORISNICKI_NALOG")
	@RequestMapping(value = "/aktivirajKorisnickiNalog/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> aktivirajKorisnickiNalog(@PathVariable String email) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
		String konkatenirani = email + ".com"; //zakucano...		
		String encryptedString = Kriptovanje.encrypt(konkatenirani);
		List<Korisnik> korisnici = korisnikService.findAll();
		
		for(int i = 0; i < korisnici.size(); i++) {
			if(korisnici.get(i).getEmail().equals(encryptedString)) {
				Korisnik k = korisnici.get(i);
				k.setAktiviranNalog(true);
				korisnikService.save(k);
				logger.info("\n\t\tKorisnik " + konkatenirani + " je aktivirao svoj korisnički nalog.\n");
				return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
			}
		}
		
		logger.info("\n\t\tNeuspešna aktivacija korisničkog naloga.\n");
		return new ResponseEntity<Korisnik>(HttpStatus.NOT_FOUND);
	}
		
	//prijava korisnika
	//@PermisijaAnotacija(naziv = "PRIJAVA")
	@RequestMapping(value = "/prijava", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> prijava(@RequestBody Korisnik requestKorisnik, HttpServletRequest request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException{
		String encryptedString = Kriptovanje.encrypt(requestKorisnik.getEmail());
		Korisnik k = korisnikService.findByEmail(encryptedString);
		
		if(k != null) {
			if(k.getEmail().equals(encryptedString) && bCryptPasswordEncoder.matches(requestKorisnik.getLozinka(), k.getLozinka()) && k.isAktiviranNalog() == true) {			
				korisnikService.setCurrentUser(k);
				logger.info("\n\t\tKorisnik " + requestKorisnik.getEmail() + " se prijavio na sistem naučne centrale.\n");
				return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
			} else {
				System.out.println("\n\t\tNe postoji korisnik sa unetim emailom i lozinkom u bazi!\n");
			}
		}
			
		logger.info("\n\t\tNeuspešna prijava korisnika na sistem.\n");
		return new ResponseEntity<Korisnik>(k, HttpStatus.NOT_FOUND);
	}
	
	//preuzimanje aktivnog korisnika
	@PreAuthorize("isAuthenticated()")
	//@PermisijaAnotacija(naziv = "GET_TRENUTNO_AKTIVAN")
	@RequestMapping(value = "/getTrenutnoAktivan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> getTrenutnoAktivan(){
		Korisnik k = korisnikService.getCurrentUser();
		
		if(k == null)
			return new ResponseEntity<Korisnik>(k, HttpStatus.NOT_FOUND);
			
		return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
	}
	
	//odjava korisnika
	//@PreAuthorize("isAuthenticated()")
	//@PermisijaAnotacija(naziv = "ODJAVA")
	@RequestMapping(value = "/odjava", method = RequestMethod.GET)
	public ResponseEntity<Korisnik> odjava() throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		Korisnik k = korisnikService.getCurrentUser();
		
		if(k != null) {
			String decryptedString = Kriptovanje.decrypt(k.getEmail());			
			SecurityContextHolder.clearContext();
			logger.info("\n\t\tKorisnik " + decryptedString + " se odjavio sa sistema naučne centrale.\n");
		}
		
		logger.info("\n\t\tNiko nije prijavljen na sistem naučne centrale.\n");
		return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
	}
	
	//azuriranje korisnika
	@PreAuthorize("isAuthenticated()")
	//@PermisijaAnotacija(naziv = "AZURIRAJ_KORISNIKA")
	@RequestMapping(value="/azurirajKorisnika", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> azurirajKorisnika(@RequestBody Korisnik requestKorisnik) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
		Korisnik k = korisnikService.getCurrentUser();
		
		if(k != null) {
			String encryptedString = Kriptovanje.encrypt(requestKorisnik.getEmail());
			
			k.setEmail(encryptedString);
			k.setLozinka(requestKorisnik.getLozinka());
			k.setIme(requestKorisnik.getIme());
			k.setPrezime(requestKorisnik.getPrezime());
			k.setGrad(requestKorisnik.getGrad());
			k.setDrzava(requestKorisnik.getDrzava());
			k.setTitula(requestKorisnik.getTitula());
			k.setAktiviranNalog(true);
			k.setRola(k.getRola()); //???
			k.setNaucneOblastiKojePokriva(k.getNaucneOblastiKojePokriva());
			k.setCasopisi(k.getCasopisi());
			
			korisnikService.save(k);
			logger.info("\n\t\tKorisnik " + requestKorisnik.getEmail() + " je ažurirao svoj korisnički nalog.\n");
			return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
		}
		
		logger.info("\n\t\tNeuspešan pokušaj ažuriranja korisnika " + requestKorisnik.getEmail() +".");
		return new ResponseEntity<Korisnik>(k, HttpStatus.NOT_FOUND);
	}
	
	//kriptovanje email adrese
	@PreAuthorize("isAuthenticated()")
	//@PermisijaAnotacija(naziv = "KRIPTOVANJE_EMAIL_ADRESE")
	@RequestMapping(value = "/kriptovanjeEmailAdrese", method = RequestMethod.GET)
	public String kriptovanjeEmailAdrese() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException{
		Korisnik k = korisnikService.getCurrentUser();
		String encryptedString = Kriptovanje.encrypt(k.getEmail());
		return encryptedString;
	}
		
	//dekriptovanje email adrese
	@PreAuthorize("isAuthenticated()")
	//@PermisijaAnotacija(naziv = "DEKRIPTOVANJE_EMAIL_ADRESE")
	@RequestMapping(value = "/dekriptovanjeEmailAdrese", method = RequestMethod.GET)
	public String dekriptovanjeEmailAdrese() throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException{
		Korisnik k = korisnikService.getCurrentUser();
		String decryptedString = Kriptovanje.decrypt(k.getEmail());
		return decryptedString;
	}
	
	//preuzimanje liste naucnih oblasti koje pokriva (vazi samo za urednka)
	@PermisijaAnotacija(naziv = "GET_NAUCNE_OBLASTI_KOJE_POKRIVA_UREDNIK")
	@RequestMapping(value = "/getNaucneOblastiKojePokrivaUrednik", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NaucnaOblast>> getNaucneOblastiKojePokrivaUrednik(){
		Korisnik k = korisnikService.getCurrentUser();	
		
		List<NaucnaOblast> naucneOblasti = new ArrayList<NaucnaOblast>();
		naucneOblasti.addAll(k.getNaucneOblastiKojePokriva());
		
		return new ResponseEntity<>(naucneOblasti, HttpStatus.OK);
	}
	
}
