package sep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sep.model.PaymentUrlAndId;
import sep.repository.PaymentUrlAndIdRepository;

@Service
public class PaymentUrlAndIdService {

	@Autowired
	private PaymentUrlAndIdRepository urlAndIdRepository;
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public PaymentUrlAndId save(PaymentUrlAndId toSave) {
		return urlAndIdRepository.save(toSave);
	}

}
