package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Person implements Serializable {

	private static final long serialVersionUID = -2414154033870368530L;
	
	private String dni;
	private String firstName;
	private String lastName;
	private String email;
	private String cellPhone = "";
	
	// Default constructor for Hibernate
	protected Person() {}
	
	public Person(String aFirstName, String aLastName, String aDNI, String anEmail) {
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setDni(aDNI);
		this.setEmail(anEmail);
	}

	/* GETTERS & SETTERS */
	
	@Id
	public String getDni() { return dni; }

	/* Protected to avoid set the primary key */
	protected void setDni(String dni) { this.dni = dni; }
	
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
