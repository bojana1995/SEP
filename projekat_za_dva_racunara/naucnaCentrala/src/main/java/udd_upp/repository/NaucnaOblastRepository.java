package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udd_upp.model.NaucnaOblast;

@Repository
public interface NaucnaOblastRepository extends JpaRepository<NaucnaOblast, Long> {

	NaucnaOblast findById(Long id);
	List<NaucnaOblast> findAll();
	NaucnaOblast findByNaziv(String naziv);
	
}
