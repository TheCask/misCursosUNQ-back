package ar.edu.unq.misCursosUNQ;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
//@Table(name="USER")
public class User extends AbstractEntity {

	private static final long serialVersionUID = 6671561417676772045L;

	private String firstName;
	private String lastName;
	private String dni;
	private String email;
	
	private List<Subject> coordinatedSubjects = new ArrayList<Subject>();
	
	//private List<Course> courses;
	
	public User() {}
	
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
	//@Column(name="user_id")
	public Long getId() { return id; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName;}

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getDni() { return dni; }

	public void setDni(String dni) { this.dni = dni; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
	
	@ManyToMany()	//cascade = { CascadeType.ALL })
	//@JoinTable(joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	public List<Subject> getCoordinatedSubjects() { return coordinatedSubjects; }

	public void setCoordinatedSubjects(List<Subject> aListCoordinatedSubjects) { 
		this.coordinatedSubjects= aListCoordinatedSubjects; 
	}
}
