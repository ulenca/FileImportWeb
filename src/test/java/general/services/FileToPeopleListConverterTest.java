package general.services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import general.model.Person;

public class FileToPeopleListConverterTest {
/*
	@Test
	public void convertFileToObjectList() {
		
		FileToPeopleListConverter converter = new FileToPeopleListConverter();
		MockMultipartFile multipartFile = new MockMultipartFile(
				"testCSV", 
				("first_name;last_name;birth_date;phone_no\r\n" + 
				"Stefan;Testowy;1988.11.11;600700800\r\n" + 
				"Maria;Ziółko;1999.1.1;555666777;\r\n" + 
				";;;\r\n" + 
				"Jolanta;Magia;2000.02.04;666000111\r\n" + 
				"\r\n" + 
				" marian;kowalewski;1950.10.01;670540120\r\n" + 
				"Zygmunt;	Stefanowicz;1991.06.01;-\r\n" + 
				"Ernest;Gąbka;1991.06.01;\r\n" + 
				"Elżbieta;Żółw;1988.03.03;670540120\r\n" + 
				"Maja;Kwiatkowska;1999.01.01").getBytes());
		
		List<Person> expectedListOfPeople = new ArrayList<>();
		
		expectedListOfPeople.add(new Person("Stefan","Testowy",LocalDate.parse("1988-11-11"), 600700800));
		expectedListOfPeople.add(new Person("Jolanta","Magia",LocalDate.parse("2000-02-04"), 666000111));
		expectedListOfPeople.add(new Person("marian","kowalewski",LocalDate.parse("1950-10-01"),670540120));
		expectedListOfPeople.add(new Person("Ernest","Gąbka",LocalDate.parse("1991-06-01")));
		expectedListOfPeople.add(new Person("Elżbieta","Żółw",LocalDate.parse("1988-03-03"),670540120));
		expectedListOfPeople.add(new Person("Maja","Kwiatkowska",LocalDate.parse("1999-01-01")));
		
	    try {
			assertEquals(expectedListOfPeople, converter.convertFileToObjectList(multipartFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	*/
}
