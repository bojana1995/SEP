package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udd_upp.model.Korpa;

@Repository
public interface KorpaRepository extends JpaRepository<Korpa, Long> {
	
	List<Korpa> findAll();
	
	void deleteByRad(Long radId);
}
