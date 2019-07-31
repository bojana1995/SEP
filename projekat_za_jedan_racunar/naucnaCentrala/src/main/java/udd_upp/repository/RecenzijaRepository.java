package udd_upp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udd_upp.model.Recenzija;

@Repository
public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {

	Recenzija findById(Long id);
	List<Recenzija> findAll();
	List<Recenzija> findByNaslov(String naslov);
	List<Recenzija> findByDatum(Date datum);
	List<Recenzija> findByVidljivaAutoru(boolean vidljivaAutoru);
	
}
