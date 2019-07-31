package udd_upp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Rola;
import udd_upp.repository.RolaRepository;

@Service
@Transactional
public class RolaService {

	@Autowired
	private RolaRepository rolaRepository;

	public Rola findById(Long id) {
		return rolaRepository.findOne(id);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Rola save(Rola rola) {
		return rolaRepository.save(rola);
	}

	public void delete(Rola rola) {
		rolaRepository.delete(rola);
	}

}
