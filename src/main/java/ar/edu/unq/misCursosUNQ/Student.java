package ar.edu.unq.misCursosUNQ;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends Person implements Serializable {

	private static final long serialVersionUID = 4376258230787104838L;

	private Integer fileNumber;
	private List<String> careers;
	private List<CourseState> takenCourseStates;
	
	// Default constructor for Hibernate
	protected Student() {}
	
	public Student(String aFirstName, String aLastName, String aDNI, String anEmail, Integer aFileNumber) {
		super(aFirstName, aLastName, aDNI, anEmail);
		setFileNumber(aFileNumber);
		setCareers(new ArrayList<String>());
		setTakenCourseStates(new ArrayList<CourseState>());
	}

	/* GETTERS & SETTERS */
	
	public Integer getFileNumber() { return fileNumber; }

	public void setFileNumber(Integer fileNumber) { this.fileNumber = fileNumber; }
	
	@OneToMany(orphanRemoval = true)
	public List<CourseState> getTakenCourseStates() { return takenCourseStates; }

	public void setTakenCourseStates(List<CourseState> courseStates) { this.takenCourseStates = courseStates; }

	@Column
    @ElementCollection()
	public List<String> getCareers() { return careers; }

	public void setCareers(List<String> careers) { this.careers = careers; }

	/* METHODS */
}
