package ar.edu.unq.misCursosUNQ;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="USER")
public class User implements Serializable {

	private static final long serialVersionUID = 6671561417676772045L;
	
	private Long dni;
	private PersonalData personalData;
	private List<Subject> coordinatedSubjects;
	private List<Course> taughtCourses;
	
	// Default constructor for Hibernate
	protected User() {}
	
	public User(String aFirstName, String aLastName, Long aDNI, String anEmail) {
		setPersonalData(new PersonalData(aFirstName, aLastName, aDNI, anEmail));
		setDni(aDNI);
		setCoordinatedSubjects(new ArrayList<Subject>());
		setTaughtCourses(new ArrayList<Course>());
	}

	/* GETTERS & SETTERS */
	
	@Id
	public Long getDni() { return dni; }

	/* Protected to avoid set the primary key */
	protected void setDni(Long dni) { this.dni = dni; }

	public PersonalData getPersonalData() { return personalData; }

	public void setPersonalData(PersonalData personalData) { this.personalData = personalData; }

	@ManyToMany(cascade = { CascadeType.ALL })
	//@JoinTable(name="person_coordinated_subjects")
	//@JoinTable(joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	public List<Subject> getCoordinatedSubjects() { return coordinatedSubjects; }
	
	public void setCoordinatedSubjects(List<Subject> coordinatedSubjects) { this.coordinatedSubjects= coordinatedSubjects; }
	
	@ManyToMany()
	public List<Course> getTaughtCourses() { return taughtCourses; }

	public void setTaughtCourses(List<Course> courses) { this.taughtCourses = courses; }

	/* METHODS */
}
