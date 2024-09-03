package exceptions;

@SuppressWarnings("serial")
public class AuthenticationException extends Exception {
	public AuthenticationException() {
		System.err.println("from Authentication exception constructor");
	}
	
	public String toString() {
		return "Authentication Failed";
	}
}
