package ar.edu.unq.misCursosUNQ;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student implements Serializable {

	private static final long serialVersionUID = 4376258230787104838L;

	private Integer fileNumber;
	private PersonalData personalData;
	private List<String> careers;
	private List<CourseState> takenCourseStates;
	
	// Default constructor for Hibernate
	protected Student() {}
	
	public Student(String aFirstName, String aLastName, Integer aDNI, String anEmail, Integer aFileNumber) {
		setPersonalData(new PersonalData(aDNI, aFirstName, aLastName, anEmail));
		setFileNumber(aFileNumber);
		setCareers(new ArrayList<String>());
		setTakenCourseStates(new ArrayList<CourseState>());
	}

	/* GETTERS & SETTERS */
	
	@Id
	public Integer getFileNumber() { return fileNumber; }

	/* Protected to avoid set the primary key */
	protected void setFileNumber(Integer fileNumber) { this.fileNumber = fileNumber; }
	
	@OneToOne
	public PersonalData getPersonalData() { return personalData; }

	public void setPersonalData(PersonalData personalData) { this.personalData = personalData; }

	@OneToMany(orphanRemoval = true)
	public List<CourseState> getTakenCourseStates() { return takenCourseStates; }

	public void setTakenCourseStates(List<CourseState> courseStates) { this.takenCourseStates = courseStates; }

	@Column
    @ElementCollection
	public List<String> getCareers() { return careers; }

	public void setCareers(List<String> careers) { this.careers = careers; }

	/* METHODS */
}
