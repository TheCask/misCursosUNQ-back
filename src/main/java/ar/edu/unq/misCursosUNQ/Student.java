package ar.edu.unq.misCursosUNQ;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student implements Serializable {
	
	private static final long serialVersionUID = 4376258230787104838L;

	private Integer fileNumber;
	private PersonalData personalData;
//	private List<String> careers;
	private List<Course> takenCourses;
	private List<Lesson> attendedLessons;
	
	// Default constructor for Hibernate
	protected Student() {}
	
	public Student(String aFirstName, String aLastName, Integer aDNI, String anEmail, Integer aFileNumber) {
		setPersonalData(new PersonalData(aDNI, aFirstName, aLastName, anEmail));
		setFileNumber(aFileNumber);
//		setCareers(new ArrayList<String>());
		setTakenCourses(new ArrayList<Course>());
		setAttendedLessons(new ArrayList<Lesson>());
	}

	/* GETTERS & SETTERS */
	
	@Id
	public Integer getFileNumber() { return fileNumber; }

	/* Protected to avoid set the primary key */
	protected void setFileNumber(Integer fileNumber) { this.fileNumber = fileNumber; }
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	public PersonalData getPersonalData() { return personalData; }

	public void setPersonalData(PersonalData personalData) { this.personalData = personalData; }

	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JsonIgnoreProperties({"students", "lessons"})
	public List<Course> getTakenCourses() { return takenCourses; }

	// Not allowed to set courses directly because database corruption
	public void setTakenCourses(List<Course> courses) { this.takenCourses = courses; }
/*
	@Column
    @ElementCollection
	public List<String> getCareers() { return careers; }

	public void setCareers(List<String> careers) { this.careers = careers; }
*/

	@ManyToMany(mappedBy = "attendantStudents", cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	@JsonIgnoreProperties({"attendantStudents", "course"})
	public List<Lesson> getAttendedLessons() { return attendedLessons; }

	// Not allowed to set lessons directly because database corruption
	private void setAttendedLessons(List<Lesson> attendedLessons) { this.attendedLessons = attendedLessons; }

	/* METHODS */
	
	public void signOnCurse(Course aCourse) {
		this.takenCourses.add(aCourse);
	}

	public void signOffCurse(Course course) {
		// Note that this method is not removing the student attended lessons of this course
		// Even if the student sing off this course, it will continue to have knowledge of assisted lessons 
		this.takenCourses.remove(course);
	}

	public void attendLesson(Lesson aLesson) {
		this.attendedLessons.add(aLesson);
	}
	
	public void unattendLesson(Lesson aLesson) {
		this.attendedLessons.remove(aLesson);
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return fileNumber != null && fileNumber.equals(((Student) o).getFileNumber());
    }
 
}
