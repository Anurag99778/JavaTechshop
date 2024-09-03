package exceptions;

@SuppressWarnings("serial")
public class DuplicateDataException extends Exception {
	public DuplicateDataException() {
		System.err.println("Duplicate data exception constructor");
	}

	@Override
	public String toString() {
		return "Duplicate Data found";
	}
	

}
