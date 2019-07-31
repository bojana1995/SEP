package udd_upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Korpa;
import udd_upp.repository.KorpaRepository;

@Service
@Transactional
public class KorpaService {
	
	@Autowired
	KorpaRepository korpaRepository;
	
	public List<Korpa> findAll() {
		return korpaRepository.findAll();
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Korpa save(Korpa korpa) {
		return korpaRepository.save(korpa);
	}
	
	public void delete(Long id) {
		korpaRepository.delete(id);
	}
	
	public void deleteAll() {
		korpaRepository.deleteAll();
	}
	public void deleteByRad(Long idRad) {
		korpaRepository.deleteByRad(idRad);
	}
}
