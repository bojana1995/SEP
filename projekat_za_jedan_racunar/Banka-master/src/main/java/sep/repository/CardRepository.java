package sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sep.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	Card findByMerchantId(Long id);
	
	Card findByPanAndSecurityCode(Long pan, Long securityCode);

}
