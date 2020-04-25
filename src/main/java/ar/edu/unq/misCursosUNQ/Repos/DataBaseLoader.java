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
		
		Course otherCourse = new Course("EPYL-C3");
		otherCourse.setCourseCode("80005-C3-CYT2");
		otherCourse.setCourseIsOpen(true);
		otherCourse.setCourseShift("Mañana");
		
		Student student1 = new Student("Eugenio", "Calcena", 28123123, "eugeniocalcena@gmail.com",14111);
		Student student2 = new Student("Elias", "Filipponi", 29123456, "eliasfilipponi@gmail.com",14222);
		Student student3 = new Student("Regina", "Falange",  33333333, "s3@gmail.com",14333);
		Student student4 = new Student("Aurelio", "Gomez",   44444444, "s4@gmail.com",14444);
		Student student5 = new Student("Nahuel", "Huapi",    55555555, "s5@gmail.com",14555);
		
		Lesson lesson1 = new Lesson(LocalDate.now());
		Lesson lesson2 = new Lesson(LocalDate.now().plusDays(1));
		Lesson lesson3 = new Lesson(LocalDate.now().plusDays(2));
		Lesson lesson4 = new Lesson(LocalDate.now().plusDays(10));
		Lesson lesson5 = new Lesson(LocalDate.now().plusDays(5));
		
		aCourse.addLesson(lesson1);
		aCourse.addLesson(lesson2);
		aCourse.addLesson(lesson3);
		
		aCourse.addStudent(student1);
		aCourse.addStudent(student2);
		aCourse.addStudent(student3);
		aCourse.addStudent(student4);
		aCourse.addStudent(student5);
		
		lesson1.setAttendance(student1);
		lesson2.setAttendance(student2);
		lesson3.setAttendance(student1);
		lesson3.setAttendance(student2);
		
		otherCourse.addLesson(lesson4);
		otherCourse.addLesson(lesson5);
		
		otherCourse.addStudent(student1);
		otherCourse.addStudent(student2);

		this.csService.createOrUpdateCourse(aCourse);
		this.csService.createOrUpdateCourse(otherCourse);
	}
	
}
