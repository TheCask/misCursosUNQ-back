package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

// Remember to include only JPA API annotations (javax.persistence.*) 
// to decouple hibernate from application code.

@Entity
public class Subject implements Serializable {
	
	private static final long serialVersionUID = -3642091487086232955L;
	
	private String subjectName;
	private String subjectCode;
	private String subjectAcronym;
	private String subjectProgramURL = "";

	private List<User> coordinators = new ArrayList<User>();
	private List<Course> courses = new ArrayList<Course>();
	
	// Default constructor for Hibernate
	protected Subject() {}
	
	public Subject(String aName, String aCode, String anAcronym) { 
		this.subjectName = aName;
		this.subjectCode = aCode;
		this.subjectAcronym = anAcronym;
	}
	
	/* GETTERS & SETTERS */
	
	@ManyToMany(mappedBy = "coordinatedSubjects")
	public List<User> getCoordinators() { return coordinators; }

	public void setCoordinators(List<User> coordinators) { this.coordinators = coordinators; }
	
	@OneToMany()
	public List<Course> getCourses() { return courses; }

	public void setCourses(List<Course> courses) { this.courses = courses; }

	public String getName() { return this.subjectName; }

	public void setName(String aName) { this.subjectName = aName; }
	
	@Id
	public String getCode() { return subjectCode; }

	/* Protected to avoid set the primary key */
	protected void setCode(String aCode) { this.subjectCode = aCode; }
	
	public String getAcronym() { return subjectAcronym; }

	public void setAcronym(String anAcronym) { this.subjectAcronym = anAcronym; }

	public String getProgram() { return subjectProgramURL; }

	public void setProgram(String aProgram) { this.subjectProgramURL = aProgram; }

	/* METHODS */
	
	// To print materia basic details in logs.
	@Override
	public String toString() {
		return "Subject [Code " + subjectCode + " | " + subjectAcronym + "]";
	}

}
