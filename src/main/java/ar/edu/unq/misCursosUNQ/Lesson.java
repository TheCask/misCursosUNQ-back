package ar.edu.unq.misCursosUNQ;

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

@Entity
public class Lesson implements Serializable{

	private static final long serialVersionUID = 2252838844078076670L;
	
	private Long lessonId;
	private Course course;
//	private LocalDate lessonDay;
	private List<Student> attendantStudents;
	
	// Default constructor for Hibernate
	public Lesson() {}
	
	public Lesson(LocalDate aDay) {
//		this.setLessonDay(aDay);
		this.setAttendantStudents(new ArrayList<Student>());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getLessonId() { return lessonId; }

	/* Protected to avoid set the primary key */
	protected void setLessonId(Long lessonId) { this.lessonId = lessonId; }
/*
	public LocalDate getLessonDay() { return lessonDay; }

	public void setLessonDay(LocalDate day) { this.lessonDay = day; }
*/
	@ManyToMany
	@JsonIgnoreProperties({"takenCourses", "attendedLessons", "careers"})
	@OrderBy("fileNumber ASC")
	public List<Student> getAttendantStudents() { return attendantStudents; }

	public void setAttendantStudents(List<Student> attendantStudents) { this.attendantStudents = attendantStudents; }

	@ManyToOne
	@JsonIgnoreProperties({"lessons", "students", "teachers"})
	public Course getCourse() { return course; }

	public void setCourse(Course course) { this.course = course; }
	
	public void setAttendance(Student aStudent) {
		// Check if the lesson course is among the taken courses of the student
		if (aStudent.getTakenCourses().contains(this.getCourse())) {
			this.attendantStudents.add(aStudent);
			aStudent.attendLesson(this);
		}
	}
	
	public void removeAttendance(Student aStudent) {
		this.attendantStudents.remove(aStudent);
		aStudent.unattendLesson(this);
	}
	
	public void removeAllAttendance() {
		this.attendantStudents.forEach(st -> st.unattendLesson(this));
		this.attendantStudents.clear();
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        return lessonId != null && lessonId.equals(((Lesson) o).lessonId);
    }

}
