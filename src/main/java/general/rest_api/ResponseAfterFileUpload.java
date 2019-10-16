package general.rest_api;

public class ResponseAfterFileUpload {
	
	//TODO dodać błędy formatowania pliku
	
	private String message;
	private String fileName;
	private int numberOfPeople;
	
	public ResponseAfterFileUpload(String message, String fileName, int numberOfPeople) {
		this.message = message;
		this.fileName = fileName;
		this.numberOfPeople = numberOfPeople;
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

	
	

}
