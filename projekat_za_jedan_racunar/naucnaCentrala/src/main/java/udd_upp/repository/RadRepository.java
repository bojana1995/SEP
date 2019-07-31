package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udd_upp.model.Rad;
import udd_upp.model.StatusRada;

@Repository
public interface RadRepository extends JpaRepository<Rad, Long> {

	Rad findById(Long id);
	List<Rad> findAll();
	List<Rad> findByNaslov(String naslov);
	List<Rad> findByKljucniPojmovi(String kljucniPojmovi);
	
}
