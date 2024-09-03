package exceptions;

@SuppressWarnings("serial")
public class IncompleteOrderException extends Exception {
	public IncompleteOrderException() {
		System.err.println("from incomplete order exception constructor");
	}
	
	public String toString() {
		return "Order Incomplete";
	}
}
