package ar.edu.unq.mis_cursos_unq.exceptions;

public class SeasonException extends Exception{

	private static final long serialVersionUID = 1495412321689857704L;

	public SeasonException(String message) { super(message); }

	public SeasonException(String message, Throwable t) { super(message, t); }
}
