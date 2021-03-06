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
import rva.jpa.Nacionalnost;
import rva.repositories.NacionalnostRepository;

@CrossOrigin
@RestController
@Api(tags= {"Nacionalnost CRUD operacije"})
public class NacionalnostRestController {

	@Autowired
	private NacionalnostRepository nacionalnostRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ApiOperation(value="Vraća kolekciju svih nacionalnosti iz baze podataka")
	@GetMapping("nacionalnost")
	public Collection<Nacionalnost> getNacionalnosti() {
		return nacionalnostRepository.findAll();
	}
	
	@ApiOperation(value="Vraća nacionalnost iz baze podataka po nekom ID-ju")
	@GetMapping("nacionalnost/{id}")
	public Nacionalnost getNacionalnostById(@PathVariable("id") Integer id) {
		return nacionalnostRepository.getById(id);
	}
	
	@ApiOperation(value="Vraća kolekciju nacionalnosti iz baze podataka po nekom nazivu")
	@GetMapping("nacionalnostNaziv/{naziv}") 
	public Collection<Nacionalnost> getNacionalnostByNaziv(@PathVariable("naziv") String naziv) {
		return nacionalnostRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@ApiOperation(value="Dodaje novu nacionalnost u bazu podataka")
	@PostMapping("nacionalnost")
	public ResponseEntity<Nacionalnost> insertNacionalnost(@RequestBody Nacionalnost nacionalnost) {
		if(!nacionalnostRepository.existsById(nacionalnost.getId())) {
			nacionalnostRepository.save(nacionalnost);
			return new ResponseEntity<Nacionalnost>(HttpStatus.OK);
		}
		return new ResponseEntity<Nacionalnost>(HttpStatus.CONFLICT);
	}
	
	@ApiOperation(value="Modifikuje postojeću nacionalnost iz baze podataka")
	@PutMapping("nacionalnost")
	public ResponseEntity<Nacionalnost> updateNacionalnost(@RequestBody Nacionalnost nacionalnost) {
		if(nacionalnostRepository.existsById(nacionalnost.getId())) {
			nacionalnostRepository.save(nacionalnost);
			return new ResponseEntity<Nacionalnost>(HttpStatus.OK);
		}
		return new ResponseEntity<Nacionalnost>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="Briše postojeću nacionalnost iz baze podataka")
	@DeleteMapping("nacionalnost/{id}")
	public ResponseEntity<Nacionalnost> deleteNacionalnost (@PathVariable("id") Integer id) {
		if(nacionalnostRepository.existsById(id)) {
			nacionalnostRepository.deleteById(id);
			
			if(id == -100)
				jdbcTemplate.execute("INSERT INTO NACIONALNOST(ID, NAZIV, SKRACENICA) VALUES(-100, 'Test nacionalnost', 'TEST')");
			return new ResponseEntity<Nacionalnost>(HttpStatus.OK);
		}
		return new ResponseEntity<Nacionalnost>(HttpStatus.NO_CONTENT);
	}
}
