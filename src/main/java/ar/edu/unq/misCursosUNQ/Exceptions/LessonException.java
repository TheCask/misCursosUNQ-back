package ar.edu.unq.misCursosUNQ.Exceptions;

public class LessonException extends Exception {
	
	private static final long serialVersionUID = -3268866217122561954L;

	public LessonException(String message) { super(message); }
    
    public LessonException(String message, Throwable t) { super(message, t); }
}
