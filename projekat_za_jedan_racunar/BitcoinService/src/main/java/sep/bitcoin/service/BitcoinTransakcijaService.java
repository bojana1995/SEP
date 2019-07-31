package sep.bitcoin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sep.bitcoin.model.BitcoinTransakcija;
import sep.bitcoin.repository.BitcoinTransakcijaRepository;

@Service
@Transactional
public class BitcoinTransakcijaService {

	@Autowired
	BitcoinTransakcijaRepository bitcoinTransakcijaRepository;
	
	public BitcoinTransakcija findOne(Long id) {
		return bitcoinTransakcijaRepository.findById(id).get();
	}
	
	public List<BitcoinTransakcija> findAll() {
		return bitcoinTransakcijaRepository.findAll();
	}
	
	public List<BitcoinTransakcija> findByNaziv(String naziv) {
		return bitcoinTransakcijaRepository.findByNaziv(naziv);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public BitcoinTransakcija save(BitcoinTransakcija transakcija) {
		return bitcoinTransakcijaRepository.save(transakcija);
	}
	
	
	public BitcoinTransakcija delete(Long id) {
		BitcoinTransakcija transakcija = bitcoinTransakcijaRepository.findById(id).get();
		
		if(transakcija != null) {
			bitcoinTransakcijaRepository.delete(transakcija);
			return transakcija;
		}
		
		throw new IllegalArgumentException("Pokušaj brisanja nepostojeće bitcoin transakcije.");
	}
	
}
