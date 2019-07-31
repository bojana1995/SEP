package sep.bitcoin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sep.bitcoin.model.BitcoinTransakcija;

@Repository
public interface BitcoinTransakcijaRepository extends JpaRepository<BitcoinTransakcija, Long> {

	List<BitcoinTransakcija> findAll();
	List<BitcoinTransakcija> findByNaziv(String naziv);
	
}
