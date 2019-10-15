package integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import general.FileImportWebApplication;
import general.database.PersonRepository;
import general.model.Person;

@ContextConfiguration(classes=FileImportWebApplication.class)
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

	@Autowired
	PersonRepository personRepo;
	@Test
	public void FindListOfPeopleFromDatabaseContainingAtLeatOnePerson() {
		Iterable<Person> resultListFromDB = personRepo.findAll();
		List<Person> people = new ArrayList<>(); 
		for(Person p:resultListFromDB) {
			people.add(p);
		}
		
		assertTrue(people.size()>0);
	}

}
