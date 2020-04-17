package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CourseDaySchedule implements Serializable{

	private static final long serialVersionUID = -8581761766618809621L;

	private Long courseDayScheduleId;
	
	private Classroom classroom;
	private DayOfWeek weekday;
	private LocalTime courseBeginHour;
	private LocalTime courseEndHour;

	// Default constructor for Hibernate
	protected CourseDaySchedule() {}
		
	public CourseDaySchedule(Classroom aClassroom, DayOfWeek aWeekDay) {
		setClassroom(aClassroom);
		setWeekday(aWeekDay);
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCourseDayScheduleId() { return courseDayScheduleId; }

	/* Protected to avoid set the primary key */
	protected void setCourseDayScheduleId(Long courseDayScheduleId) { this.courseDayScheduleId = courseDayScheduleId; }

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public Classroom getClassroom() { return classroom; }

	public void setClassroom(Classroom aClassroom) { this.classroom = aClassroom; }

	public DayOfWeek getWeekday() { return weekday; }

	public void setWeekday(DayOfWeek aWeekday) { this.weekday = aWeekday; }

	public LocalTime getCourseBeginHour() { return courseBeginHour; }

	public void setCourseBeginHour(LocalTime courseBeginHour) { this.courseBeginHour = courseBeginHour; }

	public LocalTime getCourseEndHour() { return courseEndHour; }

	public void setCourseEndHour(LocalTime courseEndHour) { this.courseEndHour = courseEndHour; }
	
}
