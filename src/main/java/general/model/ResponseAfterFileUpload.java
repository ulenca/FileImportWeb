package general.model;

import java.util.List;

public class ResponseAfterFileUpload {
	
	private String message;
	private String fileName;
	private int numberOfPeople;
	private List<String> failures;
	
	public ResponseAfterFileUpload(String message, String fileName, int numberOfPeople, List<String> failures) {
		this.message = message;
		this.fileName = fileName;
		this.numberOfPeople = numberOfPeople;
		this.failures = failures;
	}

	public ResponseAfterFileUpload(String message, String fileName) {
		this.message = message;
		this.fileName = fileName;
	}

	public String getMessage() {
		return message;
	}

	public String getFileName() {
		return fileName;
	}


	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public List<String> getFailures() {
		return failures;
	}
}
