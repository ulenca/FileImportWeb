package general.services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import general.model.ConversionResult;

public class FileToStringListConverterTest {

	@Test
	public void convertFileToStringList() {
		
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
		
    	FileToObjectListConverter<String[]> stringToStringConverter = new FileToStringListConverter();
        List<ConversionResult<String[]>> resultsAfterConversion = new ArrayList<ConversionResult<String[]>>();
		try {
			resultsAfterConversion = stringToStringConverter.convertFileToResultList(multipartFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
    	List<String[]> listOfFineRows = resultsAfterConversion.stream()
    			.filter(r -> r.isSucceeded())
    			.map(s->s.getResult())
    			.collect(Collectors.toList());
        
		List<String[]> expectedListOfPeople = new ArrayList<>();
		expectedListOfPeople.add(new String[] {"Stefan","Testowy","1988.11.11","600700800"});
		expectedListOfPeople.add(new String[] {"Jolanta","Magia","2000.02.04","666000111"});
		expectedListOfPeople.add(new String[] {"marian","kowalewski","1950.10.01","670540120"});
		expectedListOfPeople.add(new String[] {"Ernest","Gąbka","1991.06.01",""});
		expectedListOfPeople.add(new String[] {"Elżbieta","Żółw","1988.03.03","670540120"});
		expectedListOfPeople.add(new String[] {"Maja","Kwiatkowska","1999.01.01"});
	    
		assertEquals(expectedListOfPeople.size(), listOfFineRows.size());
		
		for(int i=0; i<expectedListOfPeople.size(); i++) {
			assertArrayEquals(expectedListOfPeople.get(i), listOfFineRows.get(i));
		}
	}
}
