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
	
	
	private String subjectCode;
	private String subjectName;
	private String subjectProgramURL;
	private String subjectAcronym;

	private List<User> subjectCoordinators;
	private List<Course> subjectCourses;
	
	// Default constructor for Hibernate
	protected Subject() {}
	
	public Subject(String aName, String aCode, String anAcronym) { 
		this.subjectName = aName;
		this.subjectCode = aCode;
		this.subjectAcronym = anAcronym;
		this.setProgramURL("");
		this.setCoordinators(new ArrayList<User>());
		this.setCourses(new ArrayList<Course>());
	}
	
	/* GETTERS & SETTERS */
	
	@ManyToMany(mappedBy = "coordinatedSubjects")
	public List<User> getCoordinators() { return subjectCoordinators; }

	public void setCoordinators(List<User> coordinators) { this.subjectCoordinators = coordinators; }
	
	@OneToMany
	public List<Course> getCourses() { return subjectCourses; }

	public void setCourses(List<Course> courses) { this.subjectCourses = courses; }

	public String getName() { return this.subjectName; }

	public void setName(String aName) { this.subjectName = aName; }
	
	@Id
	public String getCode() { return subjectCode; }

	/* Protected to avoid set the primary key */
	protected void setCode(String aCode) { this.subjectCode = aCode; }
	
	public String getAcronym() { return subjectAcronym; }

	public void setAcronym(String anAcronym) { this.subjectAcronym = anAcronym; }

	public String getProgramURL() { return subjectProgramURL; }

	public void setProgramURL(String aProgramURL) { this.subjectProgramURL = aProgramURL; }

	/* METHODS */
	
	// To print materia basic details in logs.
	@Override
	public String toString() {
		return "Subject [Code " + subjectCode + " | " + subjectAcronym + "]";
	}

}
