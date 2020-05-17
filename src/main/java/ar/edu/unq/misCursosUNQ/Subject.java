package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

// Remember to include only JPA API annotations (javax.persistence.*) 
// to decouple hibernate from application code.

@Entity
public class Subject implements Serializable {
	
	private static final long serialVersionUID = -3642091487086232955L;
	
	private String code;
	private String name;
	private String programURL;
	private String acronym;
	
	// Default constructor for Hibernate
	protected Subject() {}
	
	public Subject(String aName, String aCode, String anAcronym) { 
		this.name = aName;
		this.code = aCode;
		this.acronym = anAcronym;
		this.setProgramURL("");
	}
	
	/* GETTERS & SETTERS */
	
	@Id
	public String getCode() { return code; }

	/* Protected to avoid set the primary key */
	protected void setCode(String aCode) { this.code = aCode; }
	
	public String getName() { return this.name; }

	public void setName(String aName) { this.name = aName; }
	
	public String getAcronym() { return acronym; }

	public void setAcronym(String anAcronym) { this.acronym = anAcronym; }

	public String getProgramURL() { return programURL; }

	public void setProgramURL(String aProgramURL) { this.programURL = aProgramURL; }

	/* METHODS */
	
	// To print subject basic details in logs.
	@Override
	public String toString() {
		return "Subject [Code " + code + " | " + acronym + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        return code != null && code.equals(((Subject) o).getCode());
    }

}
