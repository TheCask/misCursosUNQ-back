package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="USER")
public class User extends Person implements Serializable {

	private static final long serialVersionUID = 6671561417676772045L;
	
	private List<Subject> coordinatedSubjects;
	private List<Course> taughtCourses;
	
	// Default constructor for Hibernate
	protected User() {}
	
	public User(String aFirstName, String aLastName, String aDNI, String anEmail) {
		super(aFirstName, aLastName, aDNI, anEmail);
		setCoordinatedSubjects(new ArrayList<Subject>());
		setTaughtCourses(new ArrayList<Course>());
	}

	/* GETTERS & SETTERS */
	
	@ManyToMany()	//cascade = { CascadeType.ALL })
	//@JoinTable(joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	public List<Subject> getCoordinatedSubjects() { return coordinatedSubjects; }
	
	public void setCoordinatedSubjects(List<Subject> coordinatedSubjects) { this.coordinatedSubjects= coordinatedSubjects; }
	
	@ManyToMany()
	public List<Course> getTaughtCourses() { return taughtCourses; }

	public void setTaughtCourses(List<Course> courses) { this.taughtCourses = courses; }

	/* METHODS */
}
