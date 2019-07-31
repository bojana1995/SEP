package udd_upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import udd_upp.model.Permisija;

public interface PermisijaRepository extends JpaRepository<Permisija, Long>  {

	List<Permisija> findByRoleId(Long id);
	
}
