package general.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import general.model.Person;

public class FileToPeopleListConverter implements FileToObjectListConverter<Person>{

	private static Logger logger = LogManager.getLogger(FileToPeopleListConverter.class);
	
	@Override
	public List<Person> convertFileToObjectList(MultipartFile file) throws IOException {
	
		List<String[]> listOfStrings = getListOfStrings(file);
		List<Person> listOfPeople = convertStringsToListOfPeople(listOfStrings);
		return listOfPeople;
	}

	private List<String[]> getListOfStrings(MultipartFile file) throws IOException{	
		logger.info("Trying to get list of strings");
		Reader reader = new InputStreamReader(file.getInputStream());
		
		CSVParser parser = new CSVParserBuilder()
			    .withSeparator(';')
			    .build();
		
		CSVReader csvReader = new CSVReaderBuilder(reader)
				.withCSVParser(parser)
				.withSkipLines(1)
				.withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
				.build();
		 
		List<String[]> listOfString = csvReader.readAll();
		reader.close();
		csvReader.close();
		
		List<String[]> resultList = listOfString.stream()
			.map(string->trimArray(string))
			.filter((row)->!isRowEmpty(row))
			.collect(Collectors.toList());

		return resultList;	
	}
		
	private List<Person> convertStringsToListOfPeople(List<String[]> listOfStrings){
		logger.info("Converting strings to people");
		
		List<Person> people = new ArrayList<>();		
		List<String[]> filteredStrings = filterProblematicStrings(listOfStrings);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		
		for (String[] row:filteredStrings) {						
			if(isPhonePresent(row)) {
				people.add(new Person(
						row[0], 
						row[1], 
						LocalDate.parse(row[2], formatter), 
						Integer.parseInt(row[3])));
			} else {
				people.add(new Person(
								row[0], 
								row[1],
								LocalDate.parse(row[2], formatter)));
			}
		}	
		logger.info("Finished conversion of strings to people. Got " + 
				people.size() + " people out of " + listOfStrings.size() + " records.");
		
		return people;	
	}
	
	private List<String[]> filterProblematicStrings(List<String[]> listOfStrings) {
		return listOfStrings.stream()
					.filter((row)->doesRowContainObligatoryFields(row))
					.filter((row)->doesPhoneHasProperFormat(row))
					.filter((row)->isDateProperlyFormatted(row))
					.filter((row)->doesRowContainPropoerNumberOfFields(row))
					.collect(Collectors.toList());
	}

	private boolean doesRowContainObligatoryFields(String[] row){
		if (row[0]==null || row[0].isEmpty() || row[1]==null || row[1].isEmpty() || row[2]==null || row[2].isEmpty()) {
			logger.warn("One or more of obligatory fileds (Name, Last name, date of birth) is missing in a row "
					+ java.util.Arrays.toString(row));					
			return false;
		}
		
		return true;
	}
	
	private boolean doesRowContainPropoerNumberOfFields(String[] row) {
		
		if (row.length > 4) {
			
			for(int i=4; i<row.length; i++) {
				if (row[i]!=null && !row[i].isEmpty()) {
					logger.warn("Improper number of fields in a row: " + java.util.Arrays.toString(row));
					return false;
				}
			}		
		}
		
		return true;
	}
	
	private boolean doesPhoneHasProperFormat(String[] row) {
		if (!isPhonePresent(row)) {
			return true;
		}
		if(row[3].matches("\\d{9}")) {
			return true;
		}	
		logger.warn("Phone [" + row[3] + "] has wrong format. Phone should have 9 digits");
		return false;
	}
	
	private boolean isPhonePresent(String[] row) {
		if(row.length==4) {
			if(row[3]==null || row[3].isEmpty()) {
				return false;
			} else return true;
		}
		return false;
	}
	
	private boolean isDateProperlyFormatted(String[] row) {
		if (row[2].matches("\\d{4}.\\d{2}.\\d{2}")) {
		    return true;
		}
		logger.warn("Wrong date format: [" + row[2] + "] .Date should be formatted YYYY.MM.DD");
		return false;
	}
	
	private String[] trimArray(String[] array) {
		String[] trimmedArray = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			trimmedArray[i] = array[i].trim();
		}
		return trimmedArray;
	}

	private boolean isRowEmpty(String[] row) {
		if((row.length==1 && row[0].isEmpty())) {
			return true;
		}
		return false;
	}
	
}
