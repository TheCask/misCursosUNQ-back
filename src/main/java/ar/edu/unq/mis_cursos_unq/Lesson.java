package ar.edu.unq.mis_cursos_unq;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.unq.mis_cursos_unq.exceptions.LessonException;

@Entity
public class Lesson implements Serializable{

	private static final long serialVersionUID = 2252838844078076670L;
	
	private Long lessonId;
	
	@JsonIgnoreProperties({"lessons", "students", "teachers", "subject", "evaluations"})
	private Course course;
	
	private LocalDate lessonDay;
	
	@JsonIgnoreProperties({"takenCourses", "attendedLessons", "careers"})
	private List<Student> attendantStudents;
	
	// Default constructor for Hibernate
	public Lesson() {}
	
	public Lesson(LocalDate aDay) {
		this.setLessonDay(aDay);
		this.setAttendantStudents(new ArrayList<Student>());
	}
	
	/* GETTERS & SETTERS */

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getLessonId() { return lessonId; }

	/* Protected to avoid set the primary key */
	protected void setLessonId(Long lessonId) { this.lessonId = lessonId; }

	public LocalDate getLessonDay() { return lessonDay; }

	public void setLessonDay(LocalDate day) { this.lessonDay = day; }

	@ManyToMany
	@OrderBy("fileNumber ASC")
	public List<Student> getAttendantStudents() { return attendantStudents; }

	public void setAttendantStudents(List<Student> attendantStudents) { this.attendantStudents = attendantStudents; }

	@ManyToOne
	public Course getCourse() { return course; }

	public void setCourse(Course course) { this.course = course; }
	
	/* METHODS */
	
	public void setAttendance(Student aStudent) throws LessonException {
		// Check if the lesson course is among the taken courses of the student
		if (aStudent.isInscriptedInCourse(this.getCourse())) {
			if (!this.studentAttended(aStudent)) {
				this.attendantStudents.add(aStudent);
				aStudent.attendLesson(this);
			}
		}
		else { throw new LessonException("Student is not inscribed in lesson course"); }
	}
	
	public void removeAttendance(Student aStudent) {
		if (this.attendantStudents.remove(aStudent)) {
			aStudent.unattendLesson(this);
		}
	}
	
	public void removeAllAttendance() {
		this.attendantStudents.forEach(st -> st.unattendLesson(this));
		this.attendantStudents.clear();
	}
	
	public Boolean studentAttended(Student student) {
		return this.getAttendantStudents().contains(student);
	}
	
	public Boolean attendedStudentsAreInscriptedInCourse(Course course) {
		return course.getStudents().containsAll(this.getAttendantStudents());
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        return lessonId != null && lessonId.equals(((Lesson) o).lessonId);
    }	
}