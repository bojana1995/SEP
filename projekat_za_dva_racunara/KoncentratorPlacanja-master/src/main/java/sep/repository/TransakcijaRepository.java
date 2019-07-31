package sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sep.model.Transakcija;

@Repository
public interface TransakcijaRepository  extends JpaRepository<Transakcija, Long> {

}
