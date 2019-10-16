package general.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVParser;

import general.model.Person;
import general.rest_api.PersonController;

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
			    .withIgnoreQuotations(true)
			    .build();
		
		CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
		 
		//TODO
		//checkIfTheFileContainsHeader
		//String[] header = csvReader.readNext();
		List<String[]> listOfStrings = new ArrayList<>();
		listOfStrings = csvReader.readAll();
		reader.close();
		csvReader.close();
		
		return listOfStrings;
		
	}
	
	private List<Person> convertStringsToListOfPeople(List<String[]> listOfStrings) {

		logger.info("Converting strings to people");
		
		List<Person> people = new ArrayList<>();

		for (String[] s:listOfStrings) {
			
			String[] wordsInRow = s;
			
			try {
				if(wordsInRow.length==4) {
					people.add(new Person(wordsInRow[0], wordsInRow[1], LocalDate.parse(wordsInRow[2]), Integer.parseInt(wordsInRow[3])));
				} else if (wordsInRow.length==3) {
					people.add(new Person(wordsInRow[0], wordsInRow[1], LocalDate.parse(wordsInRow[2])));
				} else {
					throw new IllegalArgumentException("Row sould be formatted in teh following manner: Name,LastName,RRRR-MM-DD,phone-optionally");
				}
			}catch(DateTimeParseException e){
				System.out.println("Wrong date format in" + wordsInRow[2] + e);
			}
		}
		
		return people;
		
	}

}
