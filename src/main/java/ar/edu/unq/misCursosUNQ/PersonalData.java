package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.io.Serializable;

@Entity
public class PersonalData implements Serializable {

	private static final long serialVersionUID = -2414154033870368530L;
	
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String cellPhone = "";
	
	// Default constructor for Hibernate
	protected PersonalData() {}
	
	public PersonalData(String aFirstName, String aLastName, String anEmail) {
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setEmail(anEmail);
	}

	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() { return id; }

	/* Protected to avoid set the primary key */
	protected void setId(Integer id) { this.id = id; }
	
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
