package general.model;

public class ConversionResult<T> {
	private final boolean succeeded;
	private final T result;
	private final String errorMessage;
	
	public ConversionResult(T result) {
		this(true, result, "");
	}
	
	public ConversionResult(String errorMessage) {
		this(false, null, errorMessage);
	}
	
	private ConversionResult(boolean succeeded, T result, String errorMessage) {
		this.succeeded = succeeded;
		this.result = result;
		this.errorMessage = errorMessage;
	}
	
	public boolean isSucceeded() {
		return succeeded;
	}
	
	public T getResult() {
		return result;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
