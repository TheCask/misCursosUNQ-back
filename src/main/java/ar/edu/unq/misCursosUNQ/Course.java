package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.unq.misCursosUNQ.Exceptions.LessonException;

@Entity
public class Course implements Serializable {
	
	private static final long serialVersionUID = -1636249307802887638L;
	
	private Integer courseId;
	//private Subject subject;
	private String courseName;
	private String courseCode;
	private String courseShift;
	private Boolean courseIsOpen;
	
//	private LocalDate courseBeginDay; 
//	private LocalDate courseEndDay;
	//private CourseWeekSchedule courseWeekSchedule = new CourseWeekSchedule();
	
//	private List<User> teachers;
	private List<Student> students;
	private List<Lesson> lessons;
	//private List<Evaluation> evaluations;
	//private List<CourseDaySchedule> weekSchedule;

	// Default constructor for Hibernate
	protected Course() {}
		
	public Course(String aName) {
//		this.setSubject(null);
		this.setCourseName(aName);
		this.setCourseCode("");
		this.setCourseShift("");
		this.setCourseIsOpen(true);
//		this.setCourseBeginDay(LocalDate.ofEpochDay(0)); // First Epoch day is 1970-01-01;
//		this.setCourseEndDay(LocalDate.ofEpochDay(0));
//		this.setTeachers(new ArrayList<User>());
		this.students = new ArrayList<Student>();
		this.lessons = new ArrayList<Lesson>();
	}
	
	/* GETTERS & SETTERS */
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@Column(columnDefinition = "integer auto_increment")
	public Integer getCourseId() { return courseId; }

	/* Protected to avoid set the primary key */
	protected void setCourseId(Integer courseId) { this.courseId = courseId; }

//	@ManyToMany(mappedBy = "taughtCourses",  cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//	public List<User> getTeachers() { return teachers; }
//
//	// Not allowed to set teachers directly because database corruption
//	protected void setTeachers(List<User> teachers) { this.teachers = teachers; }

	@ManyToMany(mappedBy = "takenCourses", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JsonIgnoreProperties({"takenCourses", "attendedLessons"})
	//@Fetch(FetchMode.SELECT)
	public List<Student> getStudents() { return students; }

	// Not allowed to set students directly because database corruption
	protected void setStudents(List<Student> students) { this.students = students; }

	@OneToMany(mappedBy = "course", cascade = { CascadeType.ALL })//, orphanRemoval = true)
	@JsonIgnoreProperties({"course", "attendantStudents"})
	public List<Lesson> getLessons() { return lessons; }

	// Not allowed to set lessons directly because database corruption
	protected void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
	
//	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
//	public Subject getSubject() { return subject; }
//
//	public void setSubject(Subject subject) { this.subject = subject; }

	/*
	@OneToMany
	public List<Evaluation> getEvaluations() { return evaluations; }

	public void setEvaluations(List<Evaluation> evaluations) { this.evaluations = evaluations; }

	@OneToMany
	public List<CourseDaySchedule> getWeekSchedule() { return weekSchedule; }

	public void setWeekSchedule(List<CourseDaySchedule> weekSchedule) { this.weekSchedule = weekSchedule; }
*/
	public String getCourseCode() { return courseCode; }

	public void setCourseCode(String code) { this.courseCode = code; }

	public String getCourseShift() { return courseShift; }

	public void setCourseShift(String courseShift) { this.courseShift = courseShift; }

	public String getCourseName() { return courseName; }

	public void setCourseName(String number) { this.courseName = number; }

	public Boolean getCourseIsOpen() { return courseIsOpen; }

	public void setCourseIsOpen(Boolean courseIsOpen) { this.courseIsOpen = courseIsOpen; }
/*
	public LocalDate getCourseBeginDay() { return courseBeginDay; }

	public void setCourseBeginDay(LocalDate courseBeginDay) { this.courseBeginDay = courseBeginDay; }

	public LocalDate getCourseEndDay() { return courseEndDay; }

	public void setCourseEndDay(LocalDate courseEndDay) { this.courseEndDay = courseEndDay; }
*/
	//public CourseWeekSchedule getCourseWeekSchedule() { return courseWeekSchedule; }

	//public void setCourseWeekSchedule(CourseWeekSchedule courseWeekSchedule) { this.courseWeekSchedule = courseWeekSchedule; }
	
	/* METHODS */
	
	public void addStudent(Student aStudent) {	
		if (!this.students.contains(aStudent)) {
			this.students.add(aStudent);
			aStudent.signOnCurse(this);
		}
	}
	
	public void removeStudent(Student aStudent) {	
		this.students.remove(aStudent);
		aStudent.signOffCurse(this);
	}
	
	public void removeStudents() {
		for(Student st : new ArrayList<>(students)) {
			removeStudent(st);
		}
	}
	
	public void addLesson(Lesson aLesson) throws LessonException {	
		// Evaluates if all students in lesson to add, are taken the present course. 
		// Only in this case adds the lesson to the course.
		if (aLesson.getAttendantStudents().stream()
				.allMatch(st -> st.getTakenCourses()
						.contains(this))) {
			this.lessons.add(aLesson);
			aLesson.setCourse(this);
		}
		else {
			throw new LessonException("There are at least one attendant student not taken this course");
		}
	}
	
	public void removeLesson(Lesson aLesson) {
		if (this.lessons.remove(aLesson)) { 
			aLesson.setCourse(null);
			// Remove a lesson implies removing attendance of students to the lesson
			aLesson.removeAllAttendance();
		}
	}
	
	public void removeLessons(List<Lesson> lessonList) {
		lessonList.forEach(ln -> this.removeLesson(ln));
	}
	
	public void removeAllLessons() {
		this.lessons.forEach(ln -> {
			ln.setCourse(null); 
			ln.removeAllAttendance();
		});
		this.lessons.clear();
	}
	
	
	// To print materia basic details in logs.
	@Override
	public String toString() {
		return "Course [Id " + courseId + " | " + courseName + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return courseId != null && courseId.equals(((Course) o).getCourseId());
    }
}