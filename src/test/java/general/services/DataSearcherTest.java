package general.services;


import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import general.model.Person;

public class DataSearcherTest {
	
	List<Person> listOfPeople = new ArrayList<>();
	Set<Person> expectedSet = new HashSet<>();
	List<Person> resultofSerchByTestMethod = new ArrayList<>();
	int numberOfPeople;
	PeopleSearcher dataSearcher = new PeopleSearcher();	


    public void finalize() {
    	listOfPeople.clear();
    	expectedSet.clear();
    	resultofSerchByTestMethod.clear();
    }
    
/*	
	@Test
	public void findTheOldestPersonWithPhoneNumberHappyPathTest() {
		
		listOfPeople.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28"),785236956));		
		listOfPeople.add(new Person("Jerzy","Grop",LocalDate.parse("1999-12-04")));
		listOfPeople.add(new Person("Aneta","Paszkowska",LocalDate.parse("2000-01-01"),483772909));
		listOfPeople.add(new Person("Artur","Boruc",LocalDate.parse("1968-02-22")));
		listOfPeople.add(new Person("Anna","Paszkowska",LocalDate.parse("1955-11-30")));
		listOfPeople.add(new Person("Weronika","Lubicz",LocalDate.parse("1946-04-01"),876456001));
		listOfPeople.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		
		expectedSet.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		
		resultofSerchByTestMethod = dataSearcher.findTheOldestPersonWithPhoneNumber(listOfPeople);
		
		Set<Person> resultofSerchByTestMethodUnordered = new HashSet<>(resultofSerchByTestMethod);

		assertEquals(expectedSet, resultofSerchByTestMethodUnordered);
		
		finalize();
				
	}
	
	@Test
	public void findTheOldestPeopleWithPhoneNumberHappyPathTest() {
		
		listOfPeople.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28"),785236956));		
		listOfPeople.add(new Person("Jerzy","Grop",LocalDate.parse("1999-12-04")));
		listOfPeople.add(new Person("Aneta","Paszkowska",LocalDate.parse("1890-01-01"),483772909));
		listOfPeople.add(new Person("Artur","Boruc",LocalDate.parse("1968-02-22")));
		listOfPeople.add(new Person("Anna","Paszkowska",LocalDate.parse("1890-11-30")));
		listOfPeople.add(new Person("Weronika","Lubicz",LocalDate.parse("1946-04-01"),876456001));
		listOfPeople.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		listOfPeople.add(new Person("Aga","Jaremko",LocalDate.parse("1890-01-01"),483771909));
		
		expectedSet.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		expectedSet.add(new Person("Aga","Jaremko",LocalDate.parse("1890-01-01"),483771909));
		expectedSet.add(new Person("Aneta","Paszkowska",LocalDate.parse("1890-01-01"),483772909));
				
		resultofSerchByTestMethod = dataSearcher.findTheOldestPersonWithPhoneNumber(listOfPeople);
		
		Set<Person> resultofSerchByTestMethodUnordered = new HashSet<>(resultofSerchByTestMethod);

		assertEquals(expectedSet, resultofSerchByTestMethodUnordered);
		
		finalize();
		
	}
	
	@Test 
	public void findTheOldestPeopleWithPhoneNumberNoSuchPeopleTest(){
		
		listOfPeople.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28")));		
		listOfPeople.add(new Person("Jerzy","Grop",LocalDate.parse("1999-12-04")));
		listOfPeople.add(new Person("Aneta","Paszkowska",LocalDate.parse("1890-01-01")));
		listOfPeople.add(new Person("Artur","Boruc",LocalDate.parse("1968-02-22")));
		
		resultofSerchByTestMethod = dataSearcher.findTheOldestPersonWithPhoneNumber(listOfPeople);
		
		Set<Person> resultofSerchByTestMethodUnordered = new HashSet<>(resultofSerchByTestMethod);

		assertEquals(expectedSet, resultofSerchByTestMethodUnordered);	
		
		finalize();
		
	}
	
	@Test 
	public void findTheOldestPeopleWithPhoneNumberOnePersonTest(){
	
		listOfPeople.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28"),785236956));
	
		expectedSet.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28"),785236956));
		
		resultofSerchByTestMethod = dataSearcher.findTheOldestPersonWithPhoneNumber(listOfPeople);
		
		Set<Person> resultofSerchByTestMethodUnordered = new HashSet<>(resultofSerchByTestMethod);
	
		assertEquals(expectedSet, resultofSerchByTestMethodUnordered);
		finalize();
	}
	
	@Test 
	public void sortPeopleAccordingToAgeTest() {
		
		listOfPeople.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28")));		
		listOfPeople.add(new Person("Jerzy","Grop",LocalDate.parse("1999-12-04")));
		listOfPeople.add(new Person("Aneta","Paszkowska",LocalDate.parse("2000-01-01"),483772909));
		listOfPeople.add(new Person("Artur","Boruc",LocalDate.parse("1968-02-22")));
		listOfPeople.add(new Person("Anna","Paszkowska",LocalDate.parse("1955-11-30")));
		listOfPeople.add(new Person("Weronika","Lubicz",LocalDate.parse("1946-04-01")));
		listOfPeople.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		
		List<Person> expectedList = new ArrayList<>();
		
		expectedList.add(new Person("Adam","Poręba",LocalDate.parse("1890-03-04"),909890988));
		expectedList.add(new Person("Weronika","Lubicz",LocalDate.parse("1946-04-01")));
		expectedList.add(new Person("Anna","Paszkowska",LocalDate.parse("1955-11-30")));
		expectedList.add(new Person("Artur","Boruc",LocalDate.parse("1968-02-22")));
		expectedList.add(new Person("Agnieszka","Radwańska",LocalDate.parse("1989-03-28")));	
		expectedList.add(new Person("Jerzy","Grop",LocalDate.parse("1999-12-04")));
		expectedList.add(new Person("Aneta","Paszkowska",LocalDate.parse("2000-01-01"),483772909));
		
		resultofSerchByTestMethod = dataSearcher.sortPeopleAccordingToAge(listOfPeople);

		assertEquals(expectedList, resultofSerchByTestMethod);
		finalize();
	}
	*/

}
