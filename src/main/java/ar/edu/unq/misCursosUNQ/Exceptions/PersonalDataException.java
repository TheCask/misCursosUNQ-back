package ar.edu.unq.misCursosUNQ.Exceptions;

public class PersonalDataException extends Exception {

	private static final long serialVersionUID = 3293542372177271864L;
	
	public PersonalDataException(String message) { super(message); }
    
    public PersonalDataException(String message, Throwable t) { super(message, t); }
}
