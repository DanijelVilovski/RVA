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
import rva.repositories.TimRepository;
import rva.jpa.Nacionalnost;
import rva.jpa.Tim;

@CrossOrigin
@RestController
@Api(tags= {"Tim CRUD operacije"})
public class TimRestController {

	@Autowired
	private TimRepository timRepository;
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	@ApiOperation(value="Vraća kolekciju svih timova iz baze podataka")
	@GetMapping("tim")
	private Collection<Tim> getTimovi() {
		return timRepository.findAll();
	}
	
	@ApiOperation(value="Vraća tim iz baze podataka po nekom ID-ju")
	@GetMapping("tim/{id}")
	public Tim getTimById(@PathVariable("id") Integer id) {
		return timRepository.getById(id);
	}
	
	@ApiOperation(value="Vraća kolekciju timova iz baze podataka po nekom nazivu")
	@GetMapping("timNaziv/{naziv}")
	public Collection<Tim> getTimByNaziv(@PathVariable("naziv") String naziv) {
		return timRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@ApiOperation(value="Dodaje novi tim u bazu podataka")
	@PostMapping("tim")
	public ResponseEntity<Tim> insertTim(@RequestBody Tim tim) {
		if(!timRepository.existsById(tim.getId())) {
			timRepository.save(tim);
			return new ResponseEntity<Tim>(HttpStatus.OK);
		}
		return new ResponseEntity<Tim>(HttpStatus.CONFLICT);
	}
	
	@ApiOperation(value="Modifikuje postojeći tim iz baze podataka")
	@PutMapping("tim")
	public ResponseEntity<Tim> updateTim(@RequestBody Tim tim) {
		if(timRepository.existsById(tim.getId())) {
			timRepository.save(tim);
			return new ResponseEntity<Tim>(HttpStatus.OK);
		}
		return new ResponseEntity<Tim>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="Briše postojeći tim iz baze podataka")
	@DeleteMapping("tim/{id}")
	public ResponseEntity<Tim> deleteTim (@PathVariable("id") Integer id) {
		if(timRepository.existsById(id)) {
			timRepository.deleteById(id);
			
			if(id == -100)
				jdbcTemplate.execute("INSERT INTO TIM(ID, NAZIV, OSNOVAN, SEDISTE, LIGA) VALUES(-100, 'Test team', to_date('01.01.1990.', 'dd.mm.yyyy.'), 'Test', 1)");
			return new ResponseEntity<Tim>(HttpStatus.OK);
		}
		return new ResponseEntity<Tim>(HttpStatus.NO_CONTENT);
	}
}
