package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CourseState implements Serializable{

	private static final long serialVersionUID = -8581761766618809621L;

	private Long courseStateId;
	
	private Course course;
	private Student student;

	// Default constructor for Hibernate
	protected CourseState() {}
		
	public CourseState(Student aStudent, Course aCourse) {
		setStudent(aStudent);
		setCourse(aCourse);
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCourseStateId() { return courseStateId; }

	protected void setCourseStateId(Long courseStateId) { this.courseStateId = courseStateId; }

	public Course getCourse() { return course; }

	public void setCourse(Course course) { this.course = course; }

	public Student getStudent() { return student; }

	public void setStudent(Student student) { this.student = student; }
	
}
