package ar.edu.unq.mis_cursos_unq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.unq.mis_cursos_unq.exceptions.LessonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class Course implements Serializable {

	private static final long serialVersionUID = -1636249307802887638L;
	
	private Integer courseId;
	
	@Size(min = 1, max = 5)
	private String courseCode;
	
	@JsonIgnore // fullCode is decoupled between back and front
	private String 	courseFullCode;
	
	@JsonIgnore // Code based on fullcode, season and year; unique in entire database
	private String uniqueCode;			
	
	@Size(min = 1, max = 10)
	private String 	courseShift;
	
	private Boolean courseIsOpen;
	
	@Min(2000) @Max(2100)
	private Integer courseYear;
	
	@Pattern(regexp = "^$|\\d\\w")
	private String courseSeason;
	
	@Size(min = 1, max = 20)
	private String courseLocation;
	
	private Subject subject;
	
//	private LocalDate courseBeginDay; 
//	private LocalDate courseEndDay;
	
	@JsonIgnoreProperties({"taughtCourses", "coordinatedSubjects", "jobDetail"})
	private List<User> teachers;
	
	@JsonIgnoreProperties({"takenCourses", "attendedLessons", "careers"})
	private List<Student> students;
	
	@JsonIgnoreProperties({"course", "attendantStudents"})
	private List<Lesson> lessons;

	@JsonIgnoreProperties({"attendantStudentCalificationMap"})	
	private List<Evaluation> evaluations;

	//	private List<CourseDaySchedule> weekSchedule;

	// Default constructor for Hibernate
	protected Course() {}
		
	public Course(String aCode, Subject aSubject, Integer aYear, String aSeason) throws SeasonException {
		this.setCourseCode(aCode);
		this.setCourseYear(aYear);
		this.setCourseSeason(aSeason);
		this.setCourseShift("");
		this.setCourseIsOpen(true);
		this.setSubject(aSubject);
//		this.setCourseBeginDay(LocalDate.ofEpochDay(0)); // First Epoch day is 1970-01-01;
//		this.setCourseEndDay(LocalDate.ofEpochDay(0));
		this.teachers = new ArrayList<User>();
		this.students = new ArrayList<Student>();
		this.lessons = new ArrayList<Lesson>();
		this.evaluations = new ArrayList<Evaluation>();
//		this.weekSchedule = new ArrayList<CourseDaySchedule>();
	}
	
	/* GETTERS & SETTERS */
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@Column(columnDefinition = "integer auto_increment")
	public Integer getCourseId() { return courseId; }

	/* Protected to avoid set the primary key */
	protected void setCourseId(Integer courseId) { this.courseId = courseId; }

	@ManyToMany(mappedBy = "takenCourses", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("fileNumber ASC")
	public List<Student> getStudents() { return students; }

	// Not allowed to set students directly because database corruption
	protected void setStudents(List<Student> students) { this.students = students; }

	@OneToMany(mappedBy = "course", cascade = { CascadeType.ALL })// orphanRemoval = true
	public List<Lesson> getLessons() { return lessons; }

	// Not allowed to set lessons directly because database corruption
	protected void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
	
	@ManyToMany(mappedBy = "taughtCourses",  cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public List<User> getTeachers() { return teachers; }

	// Not allowed to set teachers directly because database corruption
	protected void setTeachers(List<User> teachers) { this.teachers = teachers; }
	
	@OneToMany(cascade = { CascadeType.ALL })
	public List<Evaluation> getEvaluations() { return evaluations; }

	// Not allowed to set evaluations directly because database corruption
	protected void setEvaluations(List<Evaluation> evaluations) { this.evaluations = evaluations; }
	
	/*
	@OneToMany
	public List<CourseDaySchedule> getWeekSchedule() { return weekSchedule; }

	public void setWeekSchedule(List<CourseDaySchedule> weekSchedule) { this.weekSchedule = weekSchedule; }
	*/
	
	@ManyToOne(optional = false)
	public Subject getSubject() { return subject; }
	
	// TODO more tests
	// Generates the course code based on subject code and name
	public void setSubject(Subject subject) { 
		this.subject = subject;
		this.setCourseFullCode(subject.getCode());
		this.setUniqueCode(this.getCourseFullCode() + '-' 
				+ this.getCourseYear().toString() + '-'
				+ this.getCourseSeason());
	}
	
	public Integer getCourseYear() { return courseYear; }

	public void setCourseYear(Integer year) { this.courseYear = year; }
	
	public String getCourseSeason() { return courseSeason; }
	
	public void setCourseSeason(String season) { this.courseSeason = season.toUpperCase(); }
	
	public String getCourseLocation() { return courseLocation; }

	public void setCourseLocation(String location) { this.courseLocation = location; }

	public String getCourseFullCode() { return courseFullCode; }

	// Sets automatic on construction, and depends on Subject
	private void setCourseFullCode(String subjectCode) { 
		this.courseFullCode = subjectCode.replaceFirst("-", "-"+this.courseCode+"-");
	}

	@Column(unique = true)
	public String getUniqueCode() { return uniqueCode; }

	// Sets automatic on construction
	private void setUniqueCode(String uniqueCode) { this.uniqueCode = uniqueCode;}
	
	public String getCourseShift() { return courseShift; }

	public void setCourseShift(String courseShift) { this.courseShift = courseShift; }

	public String getCourseCode() { return courseCode; }

	public void setCourseCode(String code) { this.courseCode = code.toUpperCase(); }

	public Boolean getCourseIsOpen() { return courseIsOpen; }

	public void setCourseIsOpen(Boolean courseIsOpen) { this.courseIsOpen = courseIsOpen; }

	/*
	public LocalDate getCourseBeginDay() { return courseBeginDay; }

	public void setCourseBeginDay(LocalDate courseBeginDay) { this.courseBeginDay = courseBeginDay; }

	public LocalDate getCourseEndDay() { return courseEndDay; }

	public void setCourseEndDay(LocalDate courseEndDay) { this.courseEndDay = courseEndDay; }
	 */
	
	/* METHODS */
	
	public void addStudent(Student aStudent) {
		if (!aStudent.isInscriptedInCourse(this)) {
			this.students.add(aStudent);
			aStudent.signOnCourse(this);
		}
	}
	
	public void removeStudent(Student aStudent) {	
		if (this.students.remove(aStudent)) {
			aStudent.signOffCourse(this);
		}
	}
	
	public void removeAllStudents() {
		for(Student st : this.getStudents()) {
			st.signOffCourse(this);
		}
		this.getStudents().clear();
	}
	
	public void addTeacher(User aTeacher) {	
		if (!aTeacher.isTaughtCourse(this)) {
			this.teachers.add(aTeacher);
			aTeacher.assignCourse(this);
		}
	}
	
	public void removeTeacher(User aTeacher) {	
		if (this.teachers.remove(aTeacher)) {
			aTeacher.unAssignCourse(this);
		}
	}
	
	public void removeAllTeachers() {
		for(User tc : this.getTeachers()) {
			tc.unAssignCourse(this);
		}
		this.getTeachers().clear();
	}
	
	// TODO test
	public void addEvaluation(Evaluation aEvaluation) {
		if (!this.getEvaluations().contains(aEvaluation)) {
			this.evaluations.add(aEvaluation);
		}
	}
	
	// TODO test
	public void removeEvaluation(Evaluation aEvaluation) {	
		this.getEvaluations().remove(aEvaluation);
	}
	
	public void addLesson(Lesson aLesson) throws LessonException {	
		// Evaluates if all students in lesson to add, are taken the present course. 
		// Only in this case adds the lesson to the course.
		if (!this.containsLesson(aLesson)) {
			if (aLesson.attendedStudentsAreInscriptedInCourse(this)) {
				this.lessons.add(aLesson);
				aLesson.setCourse(this);
			}
			else { throw new LessonException
				("There are at least one attendant student not taken this course"); }
		}
	}

	public void removeLesson(Lesson aLesson) {
		if (this.lessons.remove(aLesson)) { 
			aLesson.setCourse(null);
			// Remove a lesson implies removing attendance of students to the lesson
			aLesson.removeAllAttendance();
		}
	}
	
	public void removeAllLessons() {
		for(Lesson ln : this.getLessons()) {
			ln.setCourse(null);
			ln.removeAllAttendance();
		}
		this.getLessons().clear();
	}
	
	protected Boolean containsLesson(Lesson aLesson) {
		return this.getLessons().contains(aLesson);
	}

	// To print subject basic details in logs.
	@Override
	public String toString() {
		return "Course [Id " + courseId + " | " + courseCode + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return courseId != null && courseId.equals(((Course) o).getCourseId());
    }
}