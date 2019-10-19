package general.services;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import general.model.Person;

import org.junit.Test;

public class PeopleWithDuplicatedPhonesSeparatorTest {
	
	@Test
	public void separatePeopleWithUniquePhonesTest() {
		List<Person> repository = new ArrayList<>();
		
		repository.add(new Person("Weronika","Lubicz",LocalDate.parse("1946-04-01"),483172909));
		repository.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		repository.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28"),785236956));		
		repository.add(new Person("Jerzy","Grop",LocalDate.parse("1968-12-04"), 878656344));
		repository.add(new Person("Aneta","Paszkowska",LocalDate.parse("2000-01-01"),483772909));
		repository.add(new Person("Artur","Boruc",LocalDate.parse("1968-02-22"),856415235));
		repository.add(new Person("Anna","Paszkowska",LocalDate.parse("1955-11-30"),789547856));

		
		List<Person> listToBeChecked = new ArrayList<>();
		
		listToBeChecked.add(new Person("Weroka","Licz",LocalDate.parse("1946-04-01"),483172909));
		listToBeChecked.add(new Person("Ad","Poba",LocalDate.parse("1890-03-04"),901890988));	
		listToBeChecked.add(new Person("Agnka","Radwka",LocalDate.parse("1989-03-28"),333236956));		
		listToBeChecked.add(new Person("rzy","Gop",LocalDate.parse("1968-12-04"), 878656344));
		listToBeChecked.add(new Person("Ana","Paszwska",LocalDate.parse("2000-01-01"),481772909));
		listToBeChecked.add(new Person("Aa","Paszska",LocalDate.parse("1955-11-30"),333236956));
		listToBeChecked.add(new Person("Luk","Pasfffka",LocalDate.parse("1955-11-30")));
		listToBeChecked.add(new Person("Luk","Pasfffka",LocalDate.parse("1955-11-30")));
	
		
		List<Person> expectedListWithUniquePhones = new ArrayList<>();
		List<Person> expectedListWithDuplicates = new ArrayList<>();

		expectedListWithDuplicates.add(new Person("Aa","Paszska",LocalDate.parse("1955-11-30"),333236956));
		expectedListWithDuplicates.add(new Person("Weroka","Licz",LocalDate.parse("1946-04-01"),483172909));
		expectedListWithDuplicates.add(new Person("rzy","Gop",LocalDate.parse("1968-12-04"), 878656344));

		expectedListWithUniquePhones.add(new Person("Ad","Poba",LocalDate.parse("1890-03-04"),901890988));
		expectedListWithUniquePhones.add(new Person("Agnka","Radwka",LocalDate.parse("1989-03-28"),333236956));
		expectedListWithUniquePhones.add(new Person("Ana","Paszwska",LocalDate.parse("2000-01-01"),481772909));
		expectedListWithUniquePhones.add(new Person("Luk","Pasfffka",LocalDate.parse("1955-11-30")));
		expectedListWithUniquePhones.add(new Person("Luk","Pasfffka",LocalDate.parse("1955-11-30")));
		
		PeopleWithDuplicatedPhonesSeparator separator = new PeopleWithDuplicatedPhonesSeparator();
		assertEquals(expectedListWithDuplicates, 
				separator.separatePeopleWithUniquePhones(listToBeChecked, repository).get("peopleWithDuplicatedPhones"));
		assertEquals(expectedListWithUniquePhones, 
				separator.separatePeopleWithUniquePhones(listToBeChecked, repository).get("peopleWithNewPhones"));
	}


}
