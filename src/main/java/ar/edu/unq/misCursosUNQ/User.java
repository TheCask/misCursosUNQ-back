package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
//@Table(name="USER")
public class User extends AbstractEntity {

	private static final long serialVersionUID = 6671561417676772045L;

	private String firstName;
	private String lastName;
	private String dni;
	private String email;
	
	@ManyToMany
	@JoinTable(name="COORDINATOR_SUBJECT")
	private List<Subject> coordinatedSubjects;
	
	//private List<Course> courses;
	
	public User(String aFirstName, String aLastName, String aDNI, String anEmail) {
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setDni(aDNI);
		this.setEmail(anEmail);
	}

	/*
	 * SETTERS & GETTERS
	 */
	
	@Override
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	public Long getId() { return id; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName;}

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getDni() { return dni; }

	public void setDni(String dni) { this.dni = dni; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
}
