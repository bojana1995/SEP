package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udd_upp.model.Casopis;

@Repository
public interface CasopisRepository extends JpaRepository<Casopis, Long> {

	Casopis findById(Long id);
	List<Casopis> findAll();
	List<Casopis> findByNaziv(String naziv);
	
}
