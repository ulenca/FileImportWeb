package general.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import general.model.Person;
import general.model.PersonInfo;

public class PeopleSearcher {
	
	private PersonInfo personInfo;
	
	public PeopleSearcher() {
		
	}
	
	public PeopleSearcher(Iterable<Person> listOfPeople) {

		this.personInfo = new PersonInfo(
				countPeople(listOfPeople),
				findTheOldestPersonWithPhoneNumber(listOfPeople),
				sortPeopleAccordingToAge(listOfPeople)
				);
	}
	
	public long countPeople(Iterable<Person> listOfPeople) {	
		return StreamSupport.stream(listOfPeople.spliterator(), false).count();
	}
	
	public List<Person> sortPeopleAccordingToAge(Iterable<Person> listOfPeople){

		return 	StreamSupport.stream(listOfPeople.spliterator(), false)
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());
	}
	
	public List<Person> findTheOldestPersonWithPhoneNumber(Iterable<Person> listOfPeople) {

		List<Person> listOfPeopleWithPhoneNumber = StreamSupport.stream(listOfPeople.spliterator(), false)
				.filter((person)-> person.getPhoneNumber()!=null)
				.collect(Collectors.toList());
		
		Optional<Person> oldestPerson = listOfPeopleWithPhoneNumber.stream()
				.max(Comparator.naturalOrder());
		
		if(!oldestPerson.isPresent()) {
			return new ArrayList<Person>();
		}
		
		return listOfPeopleWithPhoneNumber.stream()
				.filter((person)->person.getAge()==oldestPerson.get().getAge())
				.collect(Collectors.toList());
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}
}
