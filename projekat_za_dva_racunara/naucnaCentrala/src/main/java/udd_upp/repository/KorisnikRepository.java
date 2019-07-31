package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udd_upp.model.Korisnik;
import udd_upp.model.Rola;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	Korisnik findById(Long id);
	List<Korisnik> findAll();
	Korisnik findByEmail(String email);
	List<Korisnik> findByRola(Rola rola);
	
}
