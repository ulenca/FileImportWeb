package general.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import general.model.Person;

public class PeopleWithDuplicatedPhonesSeparator {
	
	
	public Map<String, List<Person>> separatePeopleWithUniquePhones(
			List<Person> peopleWithPotentiallyDuplicatePhones, Iterable<Person> peopleRepositoryWithUniquePhones){
			
		List<Person> peopleWithDuplicatedPhones = new ArrayList<>();
		List<Person> peopleWithNewPhones = new ArrayList<>();	
		
		Map<String, List<Person>> separatedDuplicatesFromInitialList = splitLists(peopleWithPotentiallyDuplicatePhones, new ArrayList<>());
		
		peopleWithDuplicatedPhones.addAll(separatedDuplicatesFromInitialList
				.get("peopleWithDuplicatedPhones"));
		
		peopleWithNewPhones.addAll(separatedDuplicatesFromInitialList
				.get("peopleWithNewPhones"));
		
		List<Integer> listOfPhonesFromRepo = getListOfPhonesFromListOfPeople(peopleRepositoryWithUniquePhones);	
		
		Map<String, List<Person>> separatedDuplicatesAgainstDB = splitLists(peopleWithNewPhones, listOfPhonesFromRepo);
		
		peopleWithNewPhones.clear();
		
		peopleWithDuplicatedPhones.addAll(separatedDuplicatesAgainstDB
				.get("peopleWithDuplicatedPhones"));
		
		peopleWithNewPhones.addAll(separatedDuplicatesAgainstDB
				.get("peopleWithNewPhones"));
		
		Map<String, List<Person>> seperatedLists = new HashMap<String, List<Person>>() ;
		
		seperatedLists.put("peopleWithNewPhones", peopleWithNewPhones);
		seperatedLists.put("peopleWithDuplicatedPhones", peopleWithDuplicatedPhones);
		
		return seperatedLists;		 
		
	}

	private Map<String, List<Person>> splitLists(List<Person> listOfPeople, List<Integer> ListOfPhones){
		
		List<Person> peopleWithDuplicatedPhones = new ArrayList<>();
		List<Person> peopleWithNewPhones = new ArrayList<>();	
		
		for(Person personFromList:listOfPeople) {
			
			boolean doesPersonHaveDuplicatedPhone = false;
			
			for(Integer phone:ListOfPhones) {	
				if(personFromList.getPhoneNumber()!=null && personFromList.getPhoneNumber().equals(phone)) {
					peopleWithDuplicatedPhones.add(personFromList);
					doesPersonHaveDuplicatedPhone = true;
					break;
				}							
			}		
			if(doesPersonHaveDuplicatedPhone==false) {
				peopleWithNewPhones.add(personFromList);
				ListOfPhones.add(personFromList.getPhoneNumber());
			}	
		}	
		
		Map<String, List<Person>> seperatedLists = new HashMap<>();
		seperatedLists.put("peopleWithNewPhones", peopleWithNewPhones);
		seperatedLists.put("peopleWithDuplicatedPhones", peopleWithDuplicatedPhones);
		
		return seperatedLists;
	}
	
	private List<Integer> getListOfPhonesFromListOfPeople(Iterable<Person> people){
		Stream<Person> streamOfPeopleFromDB = StreamSupport.stream(people.spliterator(), false);
		return streamOfPeopleFromDB
				.map(Person::getPhoneNumber)
				.filter((phone)->phone!=null)
				.distinct()
				.collect(Collectors.toList());
	}


}
