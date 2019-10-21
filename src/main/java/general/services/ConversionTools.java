package general.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConversionTools {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	
	public static boolean isPhonePresent(String[] row) {
		if(row.length==4) {
			if(row[3]==null || row[3].isEmpty()) {
				return false;
			} else return true;
		}
		return false;
	}
	
	public static boolean doesRowContainObligatoryFields(String[] row){
		if(row.length<3) {
			return false;
		}
		if (row[0]==null || row[0].isEmpty() || row[1]==null || row[1].isEmpty() || row[2]==null || row[2].isEmpty()) {			
			return false;
		}	
		return true;
	}
	
	public static boolean doesRowContainPropoerNumberOfFields(String[] row) {
		
		if (row.length > 4) {
			
			for(int i=4; i<row.length; i++) {
				if (row[i]!=null && !row[i].isEmpty()) {
					return false;
				}
			}		
		}
		return true;
	}
	
	public static boolean doesPhoneHasProperFormat(String[] row) {
		if (!isPhonePresent(row)) {
			return true;
		}
		if(row[3].matches("(?!(0))\\d{9}")) {
			return true;
		}	
		return false;
	}
	

	public static boolean isDateProperlyFormatted(String[] row) {
		if (row[2].matches("\\d{4}.\\d{2}.\\d{2}")) {
		    return true;
		}
		return false;
	}
	
	public static boolean isDateFromPast(String[] row) {
		if(LocalDate.parse(row[2] ,formatter).isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}
	
	public static String[] trimArray(String[] array) {
		String[] trimmedArray = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			trimmedArray[i] = array[i].trim();
		}
		return trimmedArray;
	}

	public static boolean isRowEmpty(String[] row) {
		if((row.length==1 && row[0].isEmpty())) {
			return true;
		}
		return false;
	}

}
