package ar.edu.unq.misCursosUNQ;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Course extends AbstractEntity {

	private static final long serialVersionUID = -1636249307802887638L;
	
	private String courseName;
	private String courseCode = "";
	private String courseShift = "";
	
	private LocalDate courseBeginDay = LocalDate.ofEpochDay(0); // First Epoch day is 1970-01-01;
	private LocalDate courseEndDay = LocalDate.ofEpochDay(0);
	//private CourseWeekSchedule courseWeekSchedule = new CourseWeekSchedule();
	
	private List<User> teachers = new ArrayList<User>();
	//private List<Student> students = new ArrayList<Student>();
	//private List<Lesson> lessons = new ArrayList<Lesson>();

	// Default constructor for Hibernate
	private Course() {}
		
	public Course(String aName) { this.setName(aName); }
	
	/* GETTERS & SETTERS */
	
	@Override
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() { return id; }

	@ManyToMany(mappedBy = "taughtCourses")
	public List<User> getTeachers() { return teachers; }

	public void setTeachers(List<User> teachers) { this.teachers = teachers; }

	public String getCode() { return courseCode; }

	public void setCode(String code) { this.courseCode = code; }

	public String getCourseShift() { return courseShift; }

	public void setCourseShift(String courseShift) { this.courseShift = courseShift; }

	public String getName() { return courseName; }

	public void setName(String number) { this.courseName = number; }

	public LocalDate getCourseBeginDay() { return courseBeginDay; }

	public void setCourseBeginDay(LocalDate courseBeginDay) { this.courseBeginDay = courseBeginDay; }

	public LocalDate getCourseEndDay() { return courseEndDay; }

	public void setCourseEndDay(LocalDate courseEndDay) { this.courseEndDay = courseEndDay; }

	//public CourseWeekSchedule getCourseWeekSchedule() { return courseWeekSchedule; }

	//public void setCourseWeekSchedule(CourseWeekSchedule courseWeekSchedule) { this.courseWeekSchedule = courseWeekSchedule; }
	
	/* METHODS */

}