package general.rest_api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import general.database.PersonRepository;
import general.database.PersonRepositoryPageable;
import general.model.ConversionResult;
import general.model.Person;
import general.model.PersonInfo;
import general.model.ResponseAfterFileUpload;
import general.model.ResponseAfterPostDelete;
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
	private PersonRepositoryPageable personRepoWithPaging;
	
	@Autowired
	EntityLinks entityLinks;

	public PersonController(PersonRepository personRepo, PersonRepositoryPageable personRepoWithPaging) {
		this.personRepo = personRepo;
		this.personRepoWithPaging = personRepoWithPaging;
	}
	
	@GetMapping("/peopleInfo")
	public PersonInfo listOfPeopleInfo(@RequestParam("page") int page){
		logger.info("Loading information about people form db");
		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("dateOfBirth").ascending());
		return new PeopleSearcher(personRepo.findAll(), personRepoWithPaging.findAll(pageRequest)).getPersonInfo();
	}
	
	@GetMapping("/peopleByName")
	public Iterable<Person> listOfPeopleFilteredByLastName(@RequestParam String lastName){
		logger.info("List of people with given last name");
		Iterable<Person> people = personRepo.findByLastName(lastName);
		return people;
	}
	
	 @PostMapping("/uploadFile")
	 public ResponseAfterFileUpload uploadCSV(@RequestParam("file") MultipartFile file, HttpServletResponse response){
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
		    	personRepo.saveAll(peopleWithUniquePhones);
		    	List<Person> peopleWithDuplicates = listOfPeople.get("peopleWithDuplicatedPhones");
		    	return new ResponseAfterFileUpload("Successful import", 
		        		file.getOriginalFilename(), 
		        		peopleWithUniquePhones.size(), 
		        		collectFailures(resultsAfterConversion, peopleWithDuplicates));
		    } catch (Exception e) {
		    	logger.error(e);
		    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		        return new ResponseAfterFileUpload("FAIL to upload: " + e.getMessage(), file.getOriginalFilename());
		    }
		 
	 }
	 
		@DeleteMapping("/deletePerson")
		public ResponseAfterPostDelete deleteOnePerson(@RequestParam Long id){
			logger.info("About to delete person with id " +id);
			personRepo.deleteById(id);
			return new ResponseAfterPostDelete("Person with id=" + id + " was deleted from db");
		}
		
		@DeleteMapping("/deleteAll")
		public ResponseAfterPostDelete deleteAll(){
			logger.info("About to delete all people from the list");
			personRepo.deleteAll();
			return new ResponseAfterPostDelete("All people were deleted from db");
		}
		
		private List<String> collectFailures(List<ConversionResult<String[]>> resultsAfterConversion, List<Person> peopleWithDuplicates){
	    	List<String> failures = new ArrayList<>();
	    	
	    	failures.addAll(resultsAfterConversion.stream()
	    			.filter(r -> !r.isSucceeded())
	    			.map(s->s.getErrorMessage())
	    			.collect(Collectors.toList()));
	    	

	    	failures.addAll(peopleWithDuplicates.stream()
	    			.map(person->new String("Person with given phone already exists in db "
			    			+ "or there are duplicates within a file: " + person.toString()))
	    			.collect(Collectors.toList()));
	    	
	    	return failures;
		}

}
