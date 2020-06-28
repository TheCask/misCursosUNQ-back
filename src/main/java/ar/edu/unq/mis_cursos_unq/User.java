package ar.edu.unq.mis_cursos_unq;

import javax.persistence.*;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
public class User implements Serializable {

	private static final long serialVersionUID = 6671561417676772045L;
	
	private Integer userId;
	
	@Field
	private Boolean isActive;
	
	@IndexedEmbedded(depth=1)
	private PersonalData personalData;
	
	@IndexedEmbedded(depth=1)
	private JobDetail jobDetail;
	
	private List<Subject> coordinatedSubjects;
	
	@JsonIgnoreProperties({"students", "lessons", "teachers", "subject"})
	private List<Course> taughtCourses;
	
	// Default constructor for Hibernate
	protected User() {}
	
	public User(String aFirstName, String aLastName, String anEmail, Integer aDNI) {
		setIsActive(true);
		setJobDetail(new JobDetail());
		setPersonalData(new PersonalData(aDNI, aFirstName, aLastName, anEmail));
		setCoordinatedSubjects(new ArrayList<Subject>());
		setTaughtCourses(new ArrayList<Course>());
	}

	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getUserId() { return userId; }

	/* Protected to avoid set the primary key */
	protected void setUserId(Integer userId) { this.userId = userId; }

	public Boolean getIsActive() { return isActive; }

	public void setIsActive(Boolean active) { this.isActive = active; }

	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
	public PersonalData getPersonalData() { return personalData; }

	public void setPersonalData(PersonalData aPersonalData) { this.personalData = aPersonalData; }

	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
	public JobDetail getJobDetail() { return jobDetail; }

	public void setJobDetail(JobDetail jobDetail) { this.jobDetail = jobDetail; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public List<Subject> getCoordinatedSubjects() { return coordinatedSubjects; }
	
	// Not allowed to set subjects directly because database corruption
	protected void setCoordinatedSubjects(List<Subject> coordinatedSubjects) { this.coordinatedSubjects= coordinatedSubjects; }
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public List<Course> getTaughtCourses() { return taughtCourses; }

	// Not allowed to set courses directly because database corruption
	protected void setTaughtCourses(List<Course> courses) { this.taughtCourses = courses; }

	/* METHODS */
	
	public void assignSubject(Subject subject) {
		if (!this.isCoordinatedSubject(subject)) {
			this.coordinatedSubjects.add(subject);
		}
	}
	
	public void unAssignSubject(Subject subject) { 
		if (this.isCoordinatedSubject(subject)) {
			this.coordinatedSubjects.remove(subject);
		}
	}
	
	public void assignCourse(Course course) {
		if (!this.isTaughtCourse(course)) {
			this.taughtCourses.add(course);
		}
	}

	public void unAssignCourse(Course course) { 
		if (this.isTaughtCourse(course)) {
			this.taughtCourses.remove(course);
		}
	}
	
	public Boolean coordinatesSubjectWithCode(String code) {
		return this.coordinatedSubjects.stream().anyMatch(sj -> sj.getCode().equals(code));
	}
	
	public Boolean isCoordinatedSubject(Subject subject) {
		return this.coordinatedSubjects.contains(subject);
	}
	
	public Boolean isTaughtCourse(Course course) {
		return this.taughtCourses.contains(course);
	}
	
	// To print User basic details in logs.
	@Override
	public String toString() {
		return "User [DNI " + personalData.getDni() + " | " + personalData.getFirstName() + " " + personalData.getLastName() + ", " + personalData.getEmail() + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return userId != null && userId.equals(((User) o).getUserId());
    }

	// TODO Test
	public List<Course> taughtCoursesByOpenState(Boolean isOpen) {
		if (isOpen) { return this.openTaughtCourses(); }
		else { return this.closeTaughtCourses(); }
	}

	// TODO Test
	public Boolean canBeInactivated() {
		return ( this.getCoordinatedSubjects().isEmpty() && this.openTaughtCourses().isEmpty() );
	}

	// TODO Test
	private List<Course> openTaughtCourses() {
		List<Course> courseList = new ArrayList<Course>();
		this.getTaughtCourses().stream().forEach(cs -> {
			if (cs.getCourseIsOpen()) { courseList.add(cs); }
		});
		return courseList;
	}
	
	// TODO Test
	private List<Course> closeTaughtCourses() {
		List<Course> courseList = new ArrayList<Course>();
		this.getTaughtCourses().stream().forEach(cs -> {
			if (!cs.getCourseIsOpen()) { courseList.add(cs); }
		});
		return courseList;
	}
}