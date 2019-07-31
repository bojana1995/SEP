package udd_upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Permisija;
import udd_upp.repository.PermisijaRepository;

@Service
@Transactional
public class PermisijaService {

	@Autowired
	private PermisijaRepository permisijaRepository;

	public Permisija findById(Long id) {
		return permisijaRepository.findOne(id);
	}

	public List<Permisija> findByRoleId(Long id) {
		return permisijaRepository.findByRoleId(id);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Permisija save(Permisija permisija) {
		return permisijaRepository.save(permisija);
	}

	public void delete(Permisija permisija) {
		permisijaRepository.delete(permisija);
	}
	
}
