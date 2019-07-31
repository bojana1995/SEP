package udd_upp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import udd_upp.dto.RadDTO;
import udd_upp.model.Korpa;
import udd_upp.model.Rad;
import udd_upp.model.TipCasopisa;
import udd_upp.service.KorpaService;
import udd_upp.service.RadService;

@RestController
@RequestMapping(value = "/korpa")
public class KorpaController {
	
	@Autowired
	KorpaService korpaService;
	
	@Autowired 
	RadService radService;
	
	@CrossOrigin
	@RequestMapping(value="/deleteAll", method = RequestMethod.GET)
	public void deleteAll() {
		korpaService.deleteAll();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable Long id) {
		korpaService.deleteByRad(id);
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RadDTO>> getAll()  {		
		List<Korpa> listaKorpi = korpaService.findAll();
		List<RadDTO> listaRadova = new ArrayList<RadDTO>(); 
		Double ukupnaCena = 0.0;
		int i = 0; 
		for(Korpa korpa : listaKorpi) {
			Rad r = radService.findOne(korpa.getRad());
			RadDTO radDto = new RadDTO();
			radDto.setId(r.getId());
			radDto.setAmount(r.getAmount());
			radDto.setApstrakt(r.getApstrakt());
			radDto.setKljucneReci(r.getKljucniPojmovi());
			radDto.setAutor(r.getAutor().getIme() + " " + r.getAutor().getPrezime());
			radDto.setNaslovRada(r.getNaslov());
			radDto.setNaucnaOblast(r.getNaucnaOblast().getNaziv());
			radDto.setCasopisRada(r.getCasopis().getNaziv());
			radDto.setTipPlacanja(r.getCasopis().getTipoviPlacanja().toString());
			if(r.getCasopis().getTipCasopisa().equals(TipCasopisa.OPEN_ACCESS)) {
				radDto.setIsOpenAccess(true);
			}else {
				radDto.setIsOpenAccess(false);
			}
			ukupnaCena = ukupnaCena + r.getAmount();
			listaRadova.add(radDto);
			if(i==0) {
				listaRadova.get(0).setMerchant_id(r.getCasopis().getMerchantId());
				listaRadova.get(0).setPaypalId(r.getCasopis().getPaypalId());
				listaRadova.get(0).setPaypalSecret(r.getCasopis().getPaypalSecret());
				listaRadova.get(0).setNazivCasopisa(r.getCasopis().getNaziv());
			}
			i++;
		}
		if(listaRadova.size()>0)
			listaRadova.get(0).setUkupnaCena(ukupnaCena);
			
		return new ResponseEntity<List<RadDTO>>(listaRadova, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
	public void addToKorpa(@PathVariable Long id)  {		
		Korpa korpaSave = new Korpa();
		korpaSave.setRad(id);
		korpaService.save(korpaSave);
	}
	
	

}
