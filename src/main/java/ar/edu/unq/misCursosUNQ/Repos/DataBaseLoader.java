package ar.edu.unq.misCursosUNQ.Repos;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Services.CourseService;

@Component
public class DataBaseLoader implements CommandLineRunner  {

	@Autowired
	private CourseService csService;
	
	@Autowired
	public DataBaseLoader(StudentRepo aStRepo, CourseRepo aCsRepo, LessonRepo aLnRepo) {
	}

	@Override
	public void run(String... strings) throws Exception {
		
		Course aCourse = new Course("LEA-C17");
		aCourse.setCourseCode("80000-C17-CYT1Y2");
		aCourse.setCourseIsOpen(false);
		aCourse.setCourseShift("Noche");
		
		Student student1 = new Student("Eugenio", "Calcena", 28123123, "eugeniocalcena@gmail.com",14555);
		Student student2 = new Student("Elias", "Filipponi", 29123456, "eliasfilipponi@gmail.com",14666);
		
		Lesson lesson1 = new Lesson(LocalDate.now());
		Lesson lesson2 = new Lesson(LocalDate.now().plusDays(1));
		Lesson lesson3 = new Lesson(LocalDate.now().plusDays(2));
		
		aCourse.addLesson(lesson1);
		aCourse.addLesson(lesson2);
		aCourse.addLesson(lesson3);
		
		aCourse.addStudent(student1);
		aCourse.addStudent(student2);
		
		lesson1.setAttendance(student1);
		lesson2.setAttendance(student2);
		lesson3.setAttendance(student1);
		lesson3.setAttendance(student2);
		
		this.csService.createOrUpdateCourse(aCourse);
	}
	
}
