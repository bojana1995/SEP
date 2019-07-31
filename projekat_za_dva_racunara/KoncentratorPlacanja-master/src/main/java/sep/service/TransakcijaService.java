package sep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sep.model.Transakcija;
import sep.repository.TransakcijaRepository;

@Service
@Transactional
public class TransakcijaService {
	
	@Autowired
	private TransakcijaRepository transakcijaRepository;
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Transakcija save(Transakcija transToSave) {
		return transakcijaRepository.save(transToSave);
	}

}
