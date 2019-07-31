package sep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sep.model.Card;
import sep.repository.CardRepository;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;

	public List<Card> getAll() {
		return cardRepository.findAll();
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Card save(Card c) {
		return cardRepository.save(c);
	}

	public Card getCardByMerchantId(Long id) {
		return cardRepository.findByMerchantId(id);
	}
	
	public Card getCardByPanAndSecurityCode(Long pan, Long securityCode) {
		return cardRepository.findByPanAndSecurityCode(pan, securityCode);
	}

}
