package ar.edu.unq.mis_cursos_unq.exceptions;

public class UserException extends Exception{

	private static final long serialVersionUID = 1495412321689857704L;

	public UserException(String message) { super(message); }

	public UserException(String message, Throwable t) { super(message, t); }
}
