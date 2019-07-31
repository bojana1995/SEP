package udd_upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.NaucnaOblast;
import udd_upp.repository.CasopisRepository;
import udd_upp.repository.KorisnikRepository;
import udd_upp.repository.NaucnaOblastRepository;
import udd_upp.repository.RadRepository;

@Service
@Transactional
public class NaucnaOblastService {

	@Autowired
	NaucnaOblastRepository naucnaOblastRepository;
	
	@Autowired
	CasopisRepository casopisRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	RadRepository radRepository;
	
	public NaucnaOblast findOne(Long id) {
		return naucnaOblastRepository.findById(id);
	}
	
	public List<NaucnaOblast> findAll() {
		return naucnaOblastRepository.findAll();
	}
	
	public NaucnaOblast findByNaziv(String naziv) {
		return naucnaOblastRepository.findByNaziv(naziv);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public NaucnaOblast save(NaucnaOblast naucnaOblast) {
		return naucnaOblastRepository.save(naucnaOblast);
	}
	
	public List<NaucnaOblast> save(List<NaucnaOblast> naucneOblasti) {
		return naucnaOblastRepository.save(naucneOblasti);
	}
	
	public NaucnaOblast delete(Long id) {
		NaucnaOblast naucnaOblast = naucnaOblastRepository.findById(id);
		
		if(naucnaOblast != null) {
			/*//kaskadno brisanje
			if(!naucnaOblast.getCasopisi().isEmpty()) {
				casopisRepository.delete(naucnaOblast.getCasopisi());
				System.out.println("\n\t\tBRISANJE NAUČNE OBLASTI - obrisani časopisi: " + naucnaOblast.getCasopisi());
			}
			
			//kaskadno brisanje
			if(!naucnaOblast.getUredniciRecenzenti().isEmpty()) {
				korisnikRepository.delete(naucnaOblast.getUredniciRecenzenti());
				System.out.println("\t\tBRISANJE NAUČNE OBLASTI - obrisani uredniciRecenzenti: " + naucnaOblast.getUredniciRecenzenti());
			}
			
			//kaskadno brisanje
			if(naucnaOblast.getRad() != null) {
				radRepository.delete(naucnaOblast.getRad());
				System.out.println("\n\t\tBRISANJE NAUČNE OBLASTI - obrisan rad: " + naucnaOblast.getRad().getNaslov());
			}
			*/
			naucnaOblastRepository.delete(naucnaOblast);
			return naucnaOblast;
		}
		
		throw new IllegalArgumentException("Pokušaj brisanja nepostojeće naučne oblasti.");
	}
	
}
