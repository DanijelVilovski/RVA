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
import rva.repositories.IgracRepository;
import rva.repositories.TimRepository;
import rva.jpa.Igrac;
import rva.jpa.Tim;

@CrossOrigin
@RestController
@Api(tags= {"Igrac CRUD operacije"})
public class IgracRestController {
	
	//ne mozemo da instanciramo interfejs, zato koristimo autowired(dependency injection)
	@Autowired
	private IgracRepository igracRepository;
	
	@Autowired
	private TimRepository timRepository;
	
	//da bismo mogli da vrsimo upit nad bazom
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ApiOperation(value="Vraća kolekciju svih igrača iz baze podataka")
	//anotacija za mapiranje http GET zahteva
	@GetMapping("igrac")
	public Collection<Igrac> getIgraci() {
		return igracRepository.findAll();
	}
	
	//ono sto je prosledjeno kao URI parametar, uzimamo pomocu @PathVariable anotacije i mapiramo u id promenljivu
	@ApiOperation(value="Vraća igrača iz baze podataka po nekom ID-ju")
	@GetMapping("igrac/{id}")
	public Igrac getIgracById(@PathVariable("id") Integer id) {
		return igracRepository.getById(id);
	}
	
	@ApiOperation(value="Vraća kolekciju igrača iz baze podataka po nekom id-ju tima")
	@GetMapping("igracTim/{idTim}")
	public Collection<Igrac> getIgracByTim(@PathVariable("idTim") Integer idTim) {
		Tim t = timRepository.getOne(idTim);
		return igracRepository.findByTim(t);
	}
	
	//pomocu anotacije @RequestBody pristupamo telu zahteva i smestamo sve u promenljivu igrac
	//povratni tip metode je ResponseEntity zato sto vracamo statusni kod kao povratnu vrednost
	@ApiOperation(value="Dodaje novog igrača u bazu podataka")
	@PostMapping("igrac") 
	public ResponseEntity<Igrac> insertIgrac(@RequestBody Igrac igrac) {
		if(!igracRepository.existsById(igrac.getId())) {
			igracRepository.save(igrac);
			return new ResponseEntity<Igrac>(HttpStatus.OK);
		}
		return new ResponseEntity<Igrac>(HttpStatus.CONFLICT);
	}
	
	@ApiOperation(value="Modifikuje postojećeg igrača iz baze podataka")
	@PutMapping("igrac")
	public ResponseEntity<Igrac> updateIgrac(@RequestBody Igrac igrac) {
		if(igracRepository.existsById(igrac.getId())) {
			//save metoda insertuje ako ne postoji ili vrsi update ako postoji
			igracRepository.save(igrac);
			return new ResponseEntity<Igrac>(HttpStatus.OK);
		}
		return new ResponseEntity<Igrac>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="Briše postojećeg igrača iz baze podataka")
	@DeleteMapping("igrac/{id}")
	public ResponseEntity<Igrac> deleteIgrac(@PathVariable("id") Integer id) {
		if(igracRepository.existsById(id)) {
			igracRepository.deleteById(id);
			
			if(id == -100) 
				jdbcTemplate.execute("INSERT INTO IGRAC(ID, IME, PREZIME, BROJ_REG, DATUM_RODJENJA, NACIONALNOST, TIM) "
						+ "VALUES (-100, 'Test ime', 'Test prezime', '000', to_date('01.01.1990.', 'dd.mm.yyyy.'), 1, 1)");
			return new ResponseEntity<Igrac>(HttpStatus.OK);
		}
		return new ResponseEntity<Igrac>(HttpStatus.NO_CONTENT);
	}
}
