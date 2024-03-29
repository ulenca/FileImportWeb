package general.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import general.model.ConversionResult;;

public class FileToStringListConverter implements FileToObjectListConverter<String[]>{

	private static Logger logger = LogManager.getLogger(FileToStringListConverter.class);
	
	@Override
	public List<ConversionResult<String[]>> convertFileToResultList(MultipartFile file) throws IOException {
	
		List<String[]> listOfStrings = getListOfStrings(file);
		return filterProblematicStrings(listOfStrings);

	}

	private List<String[]> getListOfStrings(MultipartFile file) throws IOException{	
		logger.info("Parsing file. Trying to get list of strings ou of file");
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
			.map(string->ConversionTools.trimArray(string))
			.filter((row)->!ConversionTools.isRowEmpty(row))
			.collect(Collectors.toList());

		return resultList;	
	}
		
	
	private List<ConversionResult<String[]>> filterProblematicStrings(List<String[]> listOfStrings) {
		logger.info("Going to convert list of strings to people");
		List<ConversionResult<String[]>> conversionResults = new ArrayList<>();
		
		for(String[] row:listOfStrings) {
			if(!ConversionTools.doesRowContainObligatoryFields(row)) {
				conversionResults.add(new ConversionResult<String[]>(
						"One or more of obligatory fileds (Name, Last name, date of birth) is missing in a row "
					+ java.util.Arrays.toString(row)));
				continue;
			}
			if(!ConversionTools.doesPhoneHasProperFormat(row)) {
				conversionResults.add(new ConversionResult<String[]>(
						"Phone [" + row[3] + "] has wrong format. Phone should have 9 digits and first digit must not be zero"));
				continue;
			}
			if(!ConversionTools.isDateProperlyFormatted(row)) {
				conversionResults.add(new ConversionResult<String[]>(
						"Date [" + row[2] + "] has wrong format. It should be YYYY.MM.DD"));
				continue;
			}
			if(!ConversionTools.isDateFromPast(row)) {
				conversionResults.add(new ConversionResult<String[]>(
						"Date [" + row[2] + "] cannot be from the future."));
				continue;
			}
			if(!ConversionTools.doesRowContainPropoerNumberOfFields(row)) {
				conversionResults.add(new ConversionResult<String[]>(
						"Improper number of fields in a row: " + java.util.Arrays.toString(row)));
				continue;
			}
			conversionResults.add(new ConversionResult<String[]>(row));
		}
		
		return conversionResults;
	}
	


	
}
