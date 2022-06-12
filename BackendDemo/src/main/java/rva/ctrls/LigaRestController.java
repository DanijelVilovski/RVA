package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Liga;
import rva.repositories.LigaRepository;

@CrossOrigin
@RestController
@Api(tags= {"Liga CRUD operacije"})
public class LigaRestController {

	@Autowired
	private LigaRepository ligaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ApiOperation(value="Vraća kolekciju svih liga iz baze podataka")
	@GetMapping("liga")
	private Collection<Liga> getLige() {
		return ligaRepository.findAll();
	}
	
	@ApiOperation(value="Vraća ligu iz baze podataka po nekom ID-ju")
	@GetMapping("liga/{id}")
	public Liga getLigaById(@PathVariable("id") Integer id) {
		return ligaRepository.getById(id);
	}
	
	@ApiOperation(value="Vraća kolekciju liga iz baze podataka po nekom nazivu")
	@GetMapping("ligaNaziv/{naziv}")
	public Collection<Liga> getLigaByNaziv(@PathVariable("naziv") String naziv) {
		return ligaRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@ApiOperation(value="Dodaje novu ligu u bazu podataka")
	@PostMapping("liga") 
	public ResponseEntity<Liga> insertLiga(@RequestBody Liga liga) {
		if(!ligaRepository.existsById(liga.getId())) {
			ligaRepository.save(liga);
			return new ResponseEntity<Liga>(HttpStatus.OK);
		}
		return new ResponseEntity<Liga>(HttpStatus.CONFLICT);
	}
	
	@ApiOperation(value="Modifikuje postojeću ligu iz baze podataka")
	@PutMapping("liga")
	public ResponseEntity<Liga> updateLiga(@RequestBody Liga liga) {
		if(ligaRepository.existsById(liga.getId())) {
			ligaRepository.save(liga);
			return new ResponseEntity<Liga>(HttpStatus.OK);
		}
		return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="Briše postojeću ligu iz baze podataka")
	@DeleteMapping("liga/{id}")
	public ResponseEntity<Liga> deleteLiga(@PathVariable("id") Integer id) {
		if(ligaRepository.existsById(id)) {
			ligaRepository.deleteById(id);
			
			if(id == -100)
				jdbcTemplate.execute("INSERT INTO LIGA(ID, NAZIV, OZNAKA) VALUES (-100, 'Test Liga', 'TEST')");
			return new ResponseEntity<Liga>(HttpStatus.OK);
		}
		return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
	}
	
	
}
