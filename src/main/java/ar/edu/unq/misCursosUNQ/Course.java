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
import javax.persistence.OneToMany;

@Entity
public class Course implements Serializable {

	private static final long serialVersionUID = -1636249307802887638L;
	
	private Long courseId;
	
	private String courseName;
	private String courseCode = "";
	private String courseShift = "";
	
	private LocalDate courseBeginDay = LocalDate.ofEpochDay(0); // First Epoch day is 1970-01-01;
	private LocalDate courseEndDay = LocalDate.ofEpochDay(0);
	//private CourseWeekSchedule courseWeekSchedule = new CourseWeekSchedule();
	
	private List<User> teachers = new ArrayList<User>();
	private List<Student> students = new ArrayList<Student>();
	//private List<Lesson> lessons = new ArrayList<Lesson>();

	// Default constructor for Hibernate
	protected Course() {}
		
	public Course(String aName) { this.setName(aName); }
	
	/* GETTERS & SETTERS */
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCourseId() { return courseId; }

	/* Protected to avoid set the primary key */
	protected void setCourseId(Long courseId) { this.courseId = courseId; }

	@ManyToMany(mappedBy = "taughtCourses")
	public List<User> getTeachers() { return teachers; }

	public void setTeachers(List<User> teachers) { this.teachers = teachers; }

	@OneToMany()
	public List<Student> getStudents() { return students; }

	public void setStudents(List<Student> students) { this.students = students; }

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
	
	// To print materia basic details in logs.
	@Override
	public String toString() {
		return "Course [Id " + courseId + " | " + courseName + "]";
	}

}