package ar.edu.unq.mis_cursos_unq.exceptions;

public class SubjectException extends Exception{
	
	private static final long serialVersionUID = 5270871452471704233L;

	public SubjectException(String message) { super(message); }

	public SubjectException(String message, Throwable t) { super(message, t); }
}
