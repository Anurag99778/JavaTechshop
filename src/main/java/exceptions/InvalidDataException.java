package exceptions;

@SuppressWarnings("serial")
public class InvalidDataException extends Exception {
	public InvalidDataException() {
		System.err.println("Invalid Data Exception Constructor");
	}
	
	public String toSString() {
		return "Invalid Data Entered in email field";
	}
}
