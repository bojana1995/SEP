package sep.bitcoin.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sep.bitcoin.model.BitcoinTransakcija;
import sep.bitcoin.service.BitcoinTransakcijaService;

@RestController
@RequestMapping(value = "/bitcoinTransakcija")
public class BitcoinTransakcijaController {

	@Autowired
	BitcoinTransakcijaService bitcoinTransakcijaService;
	
	//preuzimanje transakcije sa zadatim id-em
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<BitcoinTransakcija> getTransakcija(@PathVariable Long id) {
		BitcoinTransakcija transakcija = bitcoinTransakcijaService.findOne(id);
					
		if (transakcija == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
					
		return new ResponseEntity<>(transakcija, HttpStatus.OK);
	}
	
	//preuzimanje svih transakcija
	@RequestMapping(value="/getTransakcije", method = RequestMethod.GET)
	public ResponseEntity<List<BitcoinTransakcija>> getTransakcije() {
		List<BitcoinTransakcija> transakcije = bitcoinTransakcijaService.findAll();
					
		if(transakcije.equals(null)) {
			return new ResponseEntity<>(transakcije, HttpStatus.NOT_FOUND);
		}
					
		return new ResponseEntity<>(transakcije, HttpStatus.OK);
	}
	
	//dodavanje transakcije
	@CrossOrigin
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BitcoinTransakcija> dodaj(@RequestBody String requestTransakcijaString) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {		
		String idIniciraneTransakcije = requestTransakcijaString.split(", ")[0];
		String uuid = requestTransakcijaString.split(", ")[1];
		String naziv = requestTransakcijaString.split(", ")[2];
		String vremeKreiranja = requestTransakcijaString.split(", ")[3];
		String status = requestTransakcijaString.split(", ")[4];
		String uplatilac = requestTransakcijaString.split(", ")[5];
		
		BitcoinTransakcija t = new BitcoinTransakcija(idIniciraneTransakcije, uuid, naziv, vremeKreiranja, status, uplatilac);
					
		if(!t.getIdIniciraneTransakcije().isEmpty() && !t.getUuid().isEmpty() && !t.getNaziv().isEmpty() && !t.getVremeKreiranja().isEmpty() && !t.getStatus().isEmpty() && !t.getUplatilac().isEmpty()) {
			bitcoinTransakcijaService.save(t);
			return new ResponseEntity<BitcoinTransakcija>(t, HttpStatus.OK);
		}
		return new ResponseEntity<BitcoinTransakcija>(t, HttpStatus.BAD_REQUEST);
	}
	
}
