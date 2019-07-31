package udd_upp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import udd_upp.repository.ProcesRepository;
import udd_upp.service.KorisnikService;
import udd_upp.service.ProcesService;

@RestController
@RequestMapping(value = "/proces")
public class ProcesController {

	@Autowired
	ProcesService procesService;
	
	@Autowired
	ProcesRepository procesRepository;
	
	@Autowired
	KorisnikService korisnikService;
	
	
	
}
