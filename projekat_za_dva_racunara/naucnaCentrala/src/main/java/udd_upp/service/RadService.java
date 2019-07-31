package udd_upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Rad;
import udd_upp.repository.CasopisRepository;
import udd_upp.repository.KorisnikRepository;
import udd_upp.repository.NaucnaOblastRepository;
import udd_upp.repository.ProcesRepository;
import udd_upp.repository.RadRepository;
import udd_upp.repository.RecenzijaRepository;

@Service
@Transactional
public class RadService {

	@Autowired
	RadRepository radRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	NaucnaOblastRepository naucnaOblastRepository;
	
	@Autowired
	CasopisRepository casopisRepository;
	
	@Autowired
	ProcesRepository procesRepository;
	
	@Autowired
	RecenzijaRepository recenzijaRepository;
	
	public Rad findOne(Long id) {
		return radRepository.findById(id);
	}
	
	public List<Rad> findAll() {
		return radRepository.findAll();
	}
	
	public List<Rad> findByNaslov(String naslov) {
		return radRepository.findByNaslov(naslov);
	}
	
	public List<Rad> findByKljucniPojmovi(String kljucniPojmovi) {
		return radRepository.findByKljucniPojmovi(kljucniPojmovi);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Rad save(Rad rad) {
		return radRepository.save(rad);
	}
	
	public List<Rad> save(List<Rad> radovi) {
		return radRepository.save(radovi);
	}
	
	public Rad delete(Long id) {
		Rad rad = radRepository.findById(id);
		
		if(rad != null) {
			/*
			//kaskadno brisanje
			korisnikRepository.delete(rad.getKoautori());
			System.out.println("\n\t\tBRISANJE RADA - obrisani koautori: " + rad.getKoautori());
			
			//kaskadno brisanje
			korisnikRepository.delete(rad.getAutor());
			System.out.println("\n\t\tBRISANJE RADA - obrisan autor: " + rad.getAutor());
					
			//kaskadno brisanje
			korisnikRepository.delete(rad.getKoautorPodnosilacRada());
			System.out.println("\n\t\tBRISANJE RADA - obrisan koautor podnosilac rada: " + rad.getKoautorPodnosilacRada());
			
			//kaskadno brisanje
			naucnaOblastRepository.delete(rad.getNaucnaOblast());
			System.out.println("\n\t\tBRISANJE RADA - obrisana naučna oblast: " + rad.getNaucnaOblast());
			
			//kaskadno brisanje
			casopisRepository.delete(rad.getCasopisi());
			System.out.println("\n\t\tBRISANJE RADA - obrisani časopisi: " + rad.getCasopisi());
			
			//kaskadno brisanje
			korisnikRepository.delete(rad.getRecenzenti());
			System.out.println("\n\t\tBRISANJE RADA - obrisani recenzenti: " + rad.getRecenzenti());
			
			//kaskadno brisanje
			procesRepository.delete(rad.getProces());
			System.out.println("\n\t\tBRISANJE RADA - obrisan proces: " + rad.getProces());
			
			//kaskadno brisanje
			recenzijaRepository.delete(rad.getRecenzija());
			System.out.println("\n\t\tBRISANJE RADA - obrisana recenzija: " + rad.getRecenzija());
			*/
			
			radRepository.delete(rad);
			return rad;
		}
		
		throw new IllegalArgumentException("Pokušaj brisanja nepostojećeg rada.");
	}
	
}
