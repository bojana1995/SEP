package udd_upp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import udd_upp.model.Recenzija;
import udd_upp.repository.KorisnikRepository;
import udd_upp.repository.RadRepository;
import udd_upp.repository.RecenzijaRepository;

@Service
@Transactional
public class RecenzijaService {

	@Autowired
	RecenzijaRepository recenzijaRepository;
	
	@Autowired
	RadRepository radRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	public Recenzija findOne(Long id) {
		return recenzijaRepository.findById(id);
	}

	public List<Recenzija> findAll() {
		return recenzijaRepository.findAll();
	}
	
	public List<Recenzija> findByNaslov(String naslov) {
		return recenzijaRepository.findByNaslov(naslov);
	}
	
	public List<Recenzija> findByDatum(Date datum) {
		return recenzijaRepository.findByDatum(datum);
	}
	
	public List<Recenzija> findByVidljivaAutoru(boolean vidljivaAutoru) {
		return recenzijaRepository.findByVidljivaAutoru(vidljivaAutoru);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Recenzija save(Recenzija recenzija) {
		return recenzijaRepository.save(recenzija);
	}
	
	public List<Recenzija> save(List<Recenzija> recenzije) {
		return recenzijaRepository.save(recenzije);
	}
	
	public Recenzija delete(Long id) {
		Recenzija recenzija = recenzijaRepository.findById(id);
		
		if(recenzija != null) {
			/*//kaskadno brisanje
			radRepository.delete(recenzija.getRadZaKojiJeOstavljenaRecenzija());
			System.out.println("\n\t\tBRISANJE RECENZIJE - obrisana recenzija: " + recenzija.getRadZaKojiJeOstavljenaRecenzija());
			
			//kaskadno brisanje
			korisnikRepository.delete(recenzija.getRecenzent());
			System.out.println("\n\t\tBRISANJE RECENZIJE - obrisan recenzent: " + recenzija.getRecenzent());
			*/
			recenzijaRepository.delete(recenzija);
			return recenzija;
		}
		
		throw new IllegalArgumentException("Pokušaj brisanja nepostojeće recenzije.");
	}
	
}
