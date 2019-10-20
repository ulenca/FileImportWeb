package general.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import general.model.Person;

public class StringArrayToPersonConverter {
	
	private static Logger logger = LogManager.getLogger(StringArrayToPersonConverter.class);

	public List<Person> convertStringArrayToPeople(List<String[]> listOfStrings){
		
		logger.info("Converting strings to people");
		List<Person> people = new ArrayList<>();
		
			
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
			
		for (String[] row:listOfStrings) {	
			
			Integer phoneNumber = null;
					
			if(ConversionTools.isPhonePresent(row)) {
				phoneNumber = Integer.parseInt(row[3]);
			}
			
			people.add(new Person(
					row[0], 
					row[1],
					LocalDate.parse(row[2], formatter),
					phoneNumber));
		}	
		logger.info("Finished conversion of strings to people. Got " + 
				people.size() + " people.");
			
		return people;

		
	}
	

	

	
}
