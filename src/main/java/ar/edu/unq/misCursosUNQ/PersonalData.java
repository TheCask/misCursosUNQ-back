package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PersonalData implements Serializable {

	private static final long serialVersionUID = -2414154033870368530L;
	
	private Integer dni;
	private String firstName;
	private String lastName;
	private String email;
	private String cellPhone;
	
	// Default constructor for Hibernate
	protected PersonalData() {}
	
	public PersonalData(Integer aDNI, String aFirstName, String aLastName, String anEmail) {
		this.setDni(aDNI);
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setEmail(anEmail);
		this.setCellPhone("");
	}

	/* GETTERS & SETTERS */

	@Id
	public Integer getDni() { return dni; }

	/* Protected to avoid set the primary key */
	protected void setDni(Integer dni) { this.dni = dni; }
	
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName;}

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getCellPhone() { return cellPhone; }

	public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

	/* METHODS */
}
