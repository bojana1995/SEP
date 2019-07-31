package udd_upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Casopis;
import udd_upp.repository.CasopisRepository;
import udd_upp.repository.KorisnikRepository;
import udd_upp.repository.NaucnaOblastRepository;
import udd_upp.repository.RadRepository;

@Service
@Transactional
public class CasopisService {

	@Autowired
	CasopisRepository casopisRepository;
	
	@Autowired
	RadRepository radRepository;
	
	@Autowired
	NaucnaOblastRepository naucnaOblastRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	public Casopis findOne(Long id) {
		return casopisRepository.findById(id);
	}
	
	public List<Casopis> findAll() {
		return casopisRepository.findAll();
	}
	
	public List<Casopis> findByNaziv(String naziv) {
		return casopisRepository.findByNaziv(naziv);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Casopis save(Casopis casopis) {
		return casopisRepository.save(casopis);
	}
	
	public List<Casopis> save(List<Casopis> casopisi) {
		return casopisRepository.save(casopisi);
	}
	
	public Casopis delete(Long id) {
		Casopis casopis = casopisRepository.findById(id);
		
		if(casopis != null){
			casopisRepository.delete(casopis);
			return casopis;
		}
		
		throw new IllegalArgumentException("Pokušaj brisanja nepostojećeg časopisa.");
	}
	
}
