package ar.edu.unq.misCursosUNQ;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import ar.edu.unq.misCursosUNQ.Repos.PersonalDataRepo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name="USER")
public class User implements Serializable {

	private static final long serialVersionUID = 6671561417676772045L;
	
	PersonalDataRepo repo;
	
	private Integer dni;
	private PersonalData personalData;
	private JobDetail jobDetail;
	private List<Subject> coordinatedSubjects;
	private List<Course> taughtCourses;
	
	// Default constructor for Hibernate
	protected User() {}
	
	public User(String aFirstName, String aLastName, String anEmail, Integer aDNI) {
		setDni(aDNI);
		setJobDetail(new JobDetail());
		setPersonalData(new PersonalData(aDNI, aFirstName, aLastName, anEmail));
		setCoordinatedSubjects(new ArrayList<Subject>());
		setTaughtCourses(new ArrayList<Course>());
	}

	/* GETTERS & SETTERS */
	
	@Id
	public Integer getDni() { return dni; }

	/* Protected to avoid set the primary key */
	protected void setDni(Integer dni) { this.dni = dni; }

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@MapsId
	public PersonalData getPersonalData() { return personalData; }

	public void setPersonalData(PersonalData aPersonalData) { this.personalData = aPersonalData; }

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public JobDetail getJobDetail() { return jobDetail; }

	public void setJobDetail(JobDetail jobDetail) { this.jobDetail = jobDetail; }

	//@JoinTable(name= "user_coordinated_subjects", joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	@ManyToMany
	public List<Subject> getCoordinatedSubjects() { return coordinatedSubjects; }
	
	public void setCoordinatedSubjects(List<Subject> coordinatedSubjects) { this.coordinatedSubjects= coordinatedSubjects; }
	
	@ManyToMany
	public List<Course> getTaughtCourses() { return taughtCourses; }

	public void setTaughtCourses(List<Course> courses) { this.taughtCourses = courses; }

	/* METHODS */
	
	// To print User basic details in logs.
	@Override
	public String toString() {
		return "User [dni " + dni + " | " + personalData.getFirstName() + " " + personalData.getLastName() + ", " + personalData.getEmail() + "]";
	}
}
