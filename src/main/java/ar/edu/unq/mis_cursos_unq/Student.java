package ar.edu.unq.mis_cursos_unq;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student implements Serializable {
	
	private static final long serialVersionUID = 4376258230787104838L;

	private Integer 	 fileNumber;
	private PersonalData personalData;
	private List<String> careers;
	
	@JsonIgnoreProperties({"students", "lessons", "teachers", "subject", "evaluations"})
	private List<Course> takenCourses;
	
	@JsonIgnoreProperties({"attendantStudents", "course"})
	private List<Lesson> attendedLessons;
	
	// Default constructor for Hibernate
	protected Student() {}
	
	public Student(String aFirstName, String aLastName, Integer aDNI, String anEmail, Integer aFileNumber) {
		setPersonalData(new PersonalData(aDNI, aFirstName, aLastName, anEmail));
		setFileNumber(aFileNumber);
		setCareers(new ArrayList<String>());
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

	@ManyToMany//(cascade = { CascadeType.PERSIST, CascadeType.DETACH })
	@OrderBy("courseCode ASC")
	public List<Course> getTakenCourses() { return takenCourses; }

	// Not allowed to set courses directly because database corruption
	protected void setTakenCourses(List<Course> courses) { this.takenCourses = courses; }

	@ManyToMany(mappedBy = "attendantStudents", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public List<Lesson> getAttendedLessons() { return attendedLessons; }

	// Not allowed to set lessons directly because database corruption
	protected void setAttendedLessons(List<Lesson> attendedLessons) { this.attendedLessons = attendedLessons; }
	
    @ElementCollection
	public List<String> getCareers() { return careers; }

    // Not allowed to set lessons directly because database corruption
	private void setCareers(List<String> careers) { this.careers = careers; }

	/* METHODS */

	// Not use directly to prevent data corruption
	protected void signOnCourse(Course aCourse) { 
		if (!this.isInscriptedInCourse(aCourse)) {
			this.takenCourses.add(aCourse); 
		}
	}

	// Not use directly to prevent data corruption
	protected void signOffCourse(Course aCourse) {
		// Note that this method is not removing the student attended lessons of this course
		// Even if the student sing off this course, it will continue to have knowledge of assisted lessons
		if (this.isInscriptedInCourse(aCourse)) {
			this.takenCourses.remove(aCourse);
		}
	}
	
	public Boolean isInscriptedInCourse(Course aCourse) {
		return this.getTakenCourses().contains(aCourse);
	}

	public void attendLesson(Lesson aLesson) { 
		if (!this.isAttendedLesson(aLesson)) {
			this.attendedLessons.add(aLesson); 
		}
	}
		
	public void unattendLesson(Lesson aLesson) { 
		if (this.isAttendedLesson(aLesson)) {
			this.attendedLessons.remove(aLesson);
		}
	}
	
	public Boolean isAttendedLesson(Lesson aLesson) {
		return this.getAttendedLessons().contains(aLesson);
	}
	
	// To print User basic details in logs.
	@Override
	public String toString() {
		return "Student [File Number " + fileNumber + " | " + personalData.getFirstName() + " " + personalData.getLastName() + ", " + personalData.getEmail() + "]";
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return fileNumber != null && fileNumber.equals(((Student) o).getFileNumber());
    }

}
