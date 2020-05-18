package ar.edu.unq.misCursosUNQ.Exceptions;

public class SubjectException extends Exception{
	
	private static final long serialVersionUID = 5270871452471704233L;

	public SubjectException(String message) { super(message); }

	public SubjectException(String message, Throwable t) { super(message, t); }
}
