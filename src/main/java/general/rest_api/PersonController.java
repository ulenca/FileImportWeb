package general.rest_api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import general.database.PersonRepository;
import general.model.ConversionResult;
import general.model.Person;
import general.model.PersonInfo;
import general.model.ResponseAfterFileUpload;
import general.services.FileToObjectListConverter;
import general.services.FileToStringListConverter;
import general.services.PeopleSearcher;
import general.services.PeopleWithDuplicatedPhonesSeparator;
import general.services.StringArrayToPersonConverter;

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
	
	@GetMapping("/peopleInfo")
	public PersonInfo listOfPeopleInfo(){
		logger.info("Loading information about people form db");
		return new PeopleSearcher(personRepo.findAll()).getPersonInfo();
	}
	
	@GetMapping("/people")
	public Iterable<Person> listOfPeopleFilteredByLastName(@RequestParam String lastName){
		logger.info("List of people with given last name");
		Iterable<Person> people = personRepo.findByLastName(lastName);
		return people;
	}
	
	 @PostMapping("/uploadFile")
	 public ResponseAfterFileUpload uploadCSV(@RequestParam("file") MultipartFile file){
		    try {
		    	logger.info("File size: " + file.getSize() + "File format" + file.getContentType());		    
		    	
		    	FileToObjectListConverter<String[]> stringToStringConverter = new FileToStringListConverter();
		        List<ConversionResult<String[]>> resultsAfterConversion = stringToStringConverter.convertFileToResultList(file);   
		    	
		    	List<String[]> listOfFineRows = resultsAfterConversion.stream()
		    			.filter(r -> r.isSucceeded())
		    			.map(s->s.getResult())
		    			.collect(Collectors.toList());
		    	
		    	StringArrayToPersonConverter stringToPersonConverter = new StringArrayToPersonConverter(); 	
		    	List<Person> peopleFromFile = stringToPersonConverter.convertStringArrayToPeople(listOfFineRows);
		    	
		    	PeopleWithDuplicatedPhonesSeparator separator = new PeopleWithDuplicatedPhonesSeparator();	
		    	Map<String, List<Person>> listOfPeople = separator.separatePeopleWithUniquePhones(peopleFromFile, personRepo.findAll());
		    	List<Person> peopleWithUniquePhones = listOfPeople.get("peopleWithNewPhones");
		        List<Person> peopleWithDuplicates = listOfPeople.get("peopleWithDuplicatedPhones");
		    	
		    	List<String> failures = new ArrayList<>();
		    	
		    	failures.addAll(resultsAfterConversion.stream()
		    			.filter(r -> !r.isSucceeded())
		    			.map(s->s.getErrorMessage())
		    			.collect(Collectors.toList()));
		    	

		    	failures.addAll(peopleWithDuplicates.stream()
		    			.map(person->new String("Person with given phone already exists in db "
				    			+ "or there are duplicates within a file: " + person.toString()))
		    			.collect(Collectors.toList()));
		    
		    	personRepo.saveAll(peopleWithUniquePhones);
		        return new ResponseAfterFileUpload("Successful import", file.getOriginalFilename(), peopleWithUniquePhones.size(), failures);
		    } catch (Exception e) {
		    	logger.error(e);
		        return new ResponseAfterFileUpload("FAIL to upload: " + e.getMessage(), file.getOriginalFilename());
		    }
		 
	 }
	 
		@DeleteMapping("/deletePerson")
		public String deleteOnePerson(@RequestParam Long id){
			logger.info("About to delete person with id " +id);
			personRepo.deleteById(id);
			return "Person with id=" + id + "was deleted from db";
		}
		
		@DeleteMapping("/deleteAll")
		public String deleteAll(){
			logger.info("About to delete all people from the list");
			personRepo.deleteAll();
			return "All people were deleted from db";
		}

}
