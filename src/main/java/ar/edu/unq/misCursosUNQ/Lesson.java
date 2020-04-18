package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Lesson implements Serializable{

	private static final long serialVersionUID = 2252838844078076670L;
	
	private Long lessonId;
//	private LocalDate lessonDay;
	private List<Student> attendantStudents;
	
	// Default constructor for Hibernate
	public Lesson() {}
	
	public Lesson(LocalDate aDay) {
//		this.setLessonDay(aDay);
		this.setAttendantStudents(new ArrayList<Student>());
	}

	@Id
	public Long getLessonId() { return lessonId; }

	/* Protected to avoid set the primary key */
	protected void setLessonId(Long lessonId) { this.lessonId = lessonId; }
/*
	public LocalDate getLessonDay() { return lessonDay; }

	public void setLessonDay(LocalDate day) { this.lessonDay = day; }
*/
	@OneToMany
	public List<Student> getAttendantStudents() { return attendantStudents; }

	public void setAttendantStudents(List<Student> attendantStudents) { this.attendantStudents = attendantStudents; }

}
