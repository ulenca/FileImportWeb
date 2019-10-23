package general.model;

import java.util.List;

public class ResponseAfterFileUpload extends ResponseAfterPostDelete{
	
	private String fileName;
	private int numberOfPeople;
	private List<String> failures;
	
	public ResponseAfterFileUpload(String message, String fileName, int numberOfPeople, List<String> failures) {
		super(message);
		this.fileName = fileName;
		this.numberOfPeople = numberOfPeople;
		this.failures = failures;
	}

	public ResponseAfterFileUpload(String message, String fileName) {
		super(message);
		this.fileName = fileName;
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
