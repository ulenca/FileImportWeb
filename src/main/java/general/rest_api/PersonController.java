package general.rest_api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import general.database.PersonRepository;
import general.model.Person;

@RestController
@RequestMapping(path="/people", produces="application/json")
@CrossOrigin(origins="*")
public class PersonController {

	private static Logger logger = LogManager.getLogger(PersonController.class);
	private PersonRepository personRepo;
	
	@Autowired
	EntityLinks entityLinks;

	public PersonController(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}

	@GetMapping
	public Iterable<Person> listOfPeople(){
		Iterable<Person> people = personRepo.findAll();
		logger.info("List of people from db");
		people.forEach((person)-> logger.info(person.toString()));
		return people;
	}
	


}
