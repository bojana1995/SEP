package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udd_upp.model.Proces;
import udd_upp.model.TipProcesa;

@Repository
public interface ProcesRepository extends JpaRepository<Proces, Long> {

	Proces findById(Long id);
	List<Proces> findAll();
	List<Proces> findByTipProcesa(TipProcesa tipProcesa);
	
}
