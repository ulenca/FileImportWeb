package general.rest_api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import general.database.PersonRepository;
import general.model.Person;
import general.services.FileToObjectListConverter;
import general.services.FileToPeopleListConverter;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins="*")
public class PersonController {

	private static Logger logger = LogManager.getLogger(PersonController.class);
	private PersonRepository personRepo;
	
	@Autowired
	EntityLinks entityLinks;

	public PersonController(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}

	@GetMapping("/people")
	public Iterable<Person> listOfPeople(){
		Iterable<Person> people = personRepo.findAll();
		logger.info("List of people from db");
		people.forEach((person)-> logger.info(person.toString()));
		return people;
	}
	
	 @PostMapping("/uploadFile")
	 public ResponseAfterFileUpload uploadCSV(@RequestParam("file") MultipartFile file){
		    try {
		    	logger.info("File size: " + file.getSize());
		    	logger.info("File format" + file.getContentType());
		    	
		    	FileToObjectListConverter<Person> converter = new FileToPeopleListConverter();
		    	List<Person> peopleFromFile = converter.convertFileToObjectList(file);
		    	
		    	logger.info("List of people from file");
		    	peopleFromFile.forEach((p) -> logger.info(p.toString()));
		    	personRepo.saveAll(peopleFromFile);
		        return new ResponseAfterFileUpload("Successful import", file.getOriginalFilename(), peopleFromFile.size());
		    } catch (Exception e) {
		        return new ResponseAfterFileUpload("FAIL to upload", file.getOriginalFilename());
		    }
		 
	 }

}
