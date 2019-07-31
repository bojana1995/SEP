package udd_upp.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Korisnik;
import udd_upp.model.Rola;
import udd_upp.repository.KorisnikRepository;

@Service
@Transactional
public class KorisnikService {

	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void setCurrentUser(Korisnik k) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(k.getRola().getNaziv()));
        Authentication authentication = new PreAuthenticatedAuthenticationToken(k.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
	
	public Korisnik getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long id = Long.parseLong(auth.getName());
            return korisnikRepository.findOne(id);
        } catch (Exception e) {
            return null;
        }
    }
	
	public Korisnik findOne(Long id) {
		return korisnikRepository.findById(id);
	}
	
	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}
	
	public Korisnik findByEmail(String email) {
		return korisnikRepository.findByEmail(email);
	}
	
	public List<Korisnik> findByRola(Rola rola) {
		return korisnikRepository.findByRola(rola);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Korisnik save(Korisnik korisnik) {
		String file ="cestoKorisceneLozinke.txt";
		List<String> sablon = new ArrayList<String>();
		
	    try{
	        InputStream ips = new FileInputStream(file); 
	        InputStreamReader ipsr = new InputStreamReader(ips);
	        BufferedReader br = new BufferedReader(ipsr);
	        String linija;
	        
	        while ((linija = br.readLine()) != null){
	        	sablon.add(linija);
	        }
	        
	        br.close();
	    }       
	    catch (Exception e){
	        System.out.println("\n\n\t\tGreška prilikom pokušaja čitanja iz fajla sa često korišćenim lozinkama.\n");
	    } 
	    
	    //plaintext vs plaintext
	    for(int i = 0; i < sablon.size(); i++) {
	    	if(korisnik.getLozinka().equals(sablon.get(i))) {
	    		System.out.println("\n\n\t\t\tLozinka se nalazi na listi najčešće korišćenih. Morate odabrati novu lozinku.");
	    		return null;
	    	}
	    }
	    
	    //encoded
	    korisnik.setLozinka(bCryptPasswordEncoder.encode(korisnik.getLozinka()));
		return korisnikRepository.save(korisnik);
	}
	
	public List<Korisnik> save(List<Korisnik> korisnici) {
		String file ="cestoKorisceneLozinke.txt";
		List<String> sablon = new ArrayList<String>();
		
	    try{
	        InputStream ips = new FileInputStream(file); 
	        InputStreamReader ipsr = new InputStreamReader(ips);
	        BufferedReader br = new BufferedReader(ipsr);
	        String linija;
	        
	        while ((linija = br.readLine()) != null){
	        	sablon.add(linija);
	        }
	        
	        br.close();
	    }       
	    catch (Exception e){
	        System.out.println("\n\n\t\tGreška prilikom pokušaja čitanja iz fajla sa često korišćenim lozinkama.\n");
	    } 
	    
	    //plaintext vs plaintext
	    for(int i = 0; i < sablon.size(); i++) {
	    	for(int j = 0; j < korisnici.size(); j++) {
		    	if(korisnici.get(j).getLozinka().equals(sablon.get(i))) {
		    		System.out.println("\n\n\t\t\tLozinka se nalazi na listi najčešće korišćenih. Morate odabrati novu lozinku.");
		    		return null;
		    	}
	    	}
	    }
		
	    //encoded
		for(int i = 0; i < korisnici.size(); i++) {
	    	korisnici.get(i).setLozinka(bCryptPasswordEncoder.encode(korisnici.get(i).getLozinka()));
	    }
		return korisnikRepository.save(korisnici);
	}
	
	public Korisnik delete(Long id) {
		Korisnik korisnik = korisnikRepository.findById(id);
		if(korisnik == null) {
			throw new IllegalArgumentException("Pokušaj brisanja nepostojećeg korisnika.");
		}
		korisnikRepository.delete(korisnik);
		return korisnik;
	}
	
}
