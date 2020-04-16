package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="USER")
public class User extends AbstractEntity {

	private static final long serialVersionUID = 6671561417676772045L;

	private String firstName;
	private String lastName;
	private String dni;
	private String email;
	private String cellPhone = "";
	
	private List<Subject> coordinatedSubjects = new ArrayList<Subject>();
	
	private List<Course> taughtCourses = new ArrayList<Course>();
	
	// Default constructor for Hibernate
	private User() {}
	
	public User(String aFirstName, String aLastName, String aDNI, String anEmail) {
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setDni(aDNI);
		this.setEmail(anEmail);
	}

	/* GETTERS & SETTERS */
	
	@Override
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="user_id")
	public Long getId() { return id; }
	
	@ManyToMany()	//cascade = { CascadeType.ALL })
	//@JoinTable(joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	public List<Subject> getCoordinatedSubjects() { return coordinatedSubjects; }
	
	public void setCoordinatedSubjects(List<Subject> coordinatedSubjects) { this.coordinatedSubjects= coordinatedSubjects; }

	@ManyToMany()
	public List<Course> getTaughtCourses() { return taughtCourses; }

	public void setTaughtCourses(List<Course> courses) { this.taughtCourses = courses; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName;}

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getDni() { return dni; }

	public void setDni(String dni) { this.dni = dni; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getCellPhone() { return cellPhone; }

	public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

	// To print User basic details in logs.
	@Override
	public String toString() {
		return "User [id " + this.getId() + " | " + firstName + " " + lastName + ", " + dni + ", " + email + "]";
	}
}
