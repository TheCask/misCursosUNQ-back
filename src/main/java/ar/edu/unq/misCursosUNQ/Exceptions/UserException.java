package ar.edu.unq.misCursosUNQ.Exceptions;

public class UserException extends Exception{

	private static final long serialVersionUID = 1495412321689857704L;

	public UserException(String message) { super(message); }

	public UserException(String message, Throwable t) { super(message, t); }
}
