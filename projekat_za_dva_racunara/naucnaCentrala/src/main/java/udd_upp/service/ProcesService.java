package udd_upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Proces;
import udd_upp.model.TipProcesa;
import udd_upp.repository.ProcesRepository;
import udd_upp.repository.RadRepository;

@Service
@Transactional
public class ProcesService {

	@Autowired
	ProcesRepository procesRepository;
	
	@Autowired
	RadRepository radRepository;
	
	public Proces findOne(Long id) {
		return procesRepository.findById(id);
	}
	
	public List<Proces> findAll() {
		return procesRepository.findAll();
	}
	
	public List<Proces> findByTipProcesa(TipProcesa tipProcesa) {
		return procesRepository.findByTipProcesa(tipProcesa);
	}
	
	public Proces save(Proces proces) {
		return procesRepository.save(proces);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public List<Proces> save(List<Proces> procesi) {
		return procesRepository.save(procesi);
	}
	
	public Proces delete(Long id) {
		Proces proces = procesRepository.findById(id);
		
		if(proces != null) {
			/*//kaskadnno brisanje
			radRepository.delete(proces.getRadKojiJeUProcesu());
			System.out.println("\n\t\tBRISANJE PROCESA - obrisan rad koji je u procesu: " + proces.getRadKojiJeUProcesu());
			*/
			procesRepository.delete(proces);
			return proces;
		}
		
		throw new IllegalArgumentException("Pokušaj brisanja nepostojećeg procesa.");
	}
	
}
