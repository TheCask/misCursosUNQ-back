package ar.edu.unq.misCursosUNQ;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

// Remember to include only JPA API annotations (javax.persistence.*) 
// to decouple hibernate from application code.

@Entity
//@Table(name="SUBJECT")
public class Subject extends AbstractEntity {
	
	private static final long serialVersionUID = -3642091487086232955L;

	//@Column(name="name")
	private String name;
	private String code;
	private String acronym;
	private String programURL = "";

	private List<User> coordinators = new ArrayList<User>();
	
	//@ManyToMany(mappedBy = "?????")
	//public List<User> teachers;
	
	// Default constructor for Hibernate
	private Subject() {}
	
	public Subject(String aName, String aCode, String anAcronym) { 
		this.name = aName;
		this.code = aCode;
		this.acronym = anAcronym;
	}
	
	/* GETTERS & SETTERS */
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="subject_id")
	public Long getId() { return id; }

	public String getName() { return this.name; }

	public void setName(String aName) { this.name = aName; }
	
	public String getCode() { return code; }

	public void setCode(String aCode) { this.code = aCode; }
	
	public String getAcronym() { return acronym; }

	public void setAcronym(String anAcronym) { this.acronym = anAcronym; }

	public String getProgram() { return programURL; }

	public void setProgram(String aProgram) { this.programURL = aProgram; }

	@ManyToMany(mappedBy = "coordinatedSubjects")
	public List<User> getCoordinators() { return coordinators; }

	public void setCoordinators(List<User> coordinators) { this.coordinators = coordinators; }

	// To print materia basic details in logs.
	@Override
	public String toString() {
		return "Subject [id " + this.getId() + " | " + code + ", " + acronym + "]";
	}

}
