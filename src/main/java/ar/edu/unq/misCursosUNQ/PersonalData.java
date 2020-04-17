package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PersonalData implements Serializable {

	private static final long serialVersionUID = -2414154033870368530L;
	
	private Long dni;
	private String firstName;
	private String lastName;
	private String email;
	private String cellPhone = "";
	
	// Default constructor for Hibernate
	protected PersonalData() {}
	
	public PersonalData(String aFirstName, String aLastName, Long aDNI, String anEmail) {
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setDni(aDNI);
		this.setEmail(anEmail);
	}

	/* GETTERS & SETTERS */
	
	@Id
	public Long getDni() { return dni; }

	/* Protected to avoid set the primary key */
	protected void setDni(Long dni) { this.dni = dni; }
	
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName;}

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getCellPhone() { return cellPhone; }

	public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

	/* METHODS */
	
	// To print User basic details in logs.
	@Override
	public String toString() {
		return "Person [dni " + dni + " | " + firstName + " " + lastName + ", " + email + "]";
	}
}
