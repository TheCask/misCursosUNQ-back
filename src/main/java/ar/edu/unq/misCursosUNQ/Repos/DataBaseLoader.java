package ar.edu.unq.misCursosUNQ.Repos;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.User;
import ar.edu.unq.misCursosUNQ.Services.CourseService;
import ar.edu.unq.misCursosUNQ.Services.SubjectService;

@Component
public class DataBaseLoader implements CommandLineRunner  {

	@Autowired
	private CourseService csService;
	
	@Autowired
	private SubjectService sbService;
	
	@Autowired
	public DataBaseLoader(StudentRepo aStRepo, CourseRepo aCsRepo, LessonRepo aLnRepo, UserRepo aUsRepo) {
	}

	@Override
	public void run(String... strings) throws Exception {
		
		Subject lea = new Subject("Lectura y Escritura Académica", "80000-CyT1y2", "LEA");
		Subject epyl = new Subject("Elementos de Programación y Lógica", "80005-CyT2", "EPYL");
		Subject icfq = new Subject("Introducción al Conocimiento de la Física y la Química", "80004-CyT1", "ICFYQ");
		
		this.sbService.createOrUpdateSubject(lea);
		this.sbService.createOrUpdateSubject(epyl);
		this.sbService.createOrUpdateSubject(icfq);
		
		Course aCourse = new Course("C17", lea);
		aCourse.setCourseIsOpen(false);
		aCourse.setCourseShift("Noche");
		
		Course otherCourse = new Course("C3", epyl);
		otherCourse.setCourseIsOpen(true);
		otherCourse.setCourseShift("Mañana");
		
		Course anotherCourse = new Course("C12", icfq);
		anotherCourse.setCourseIsOpen(true);
		anotherCourse.setCourseShift("Tarde");
		
		User teacher1 = new User("El Profe", "Romero", "el_profe@unq.edu.ar", 12658953);
		
		Student student1 = new Student("Eugenio", "Calcena", 28123123, "eugeniocalcena@gmail.com",14111);
		Student student2 = new Student("Elias", "Filipponi", 29123456, "eliasfilipponi@gmail.com",14222);
		Student student3 = new Student("Regina", "Falange",  33333333, "regina@gmail.com",14333);
		Student student4 = new Student("Aurelio", "Gomez",   44444444, "aurelio_gomez@gmail.com",14444);
		Student student5 = new Student("Nahuel", "Huapi",    55555555, "huapi.n@gmail.com",14555);
		
		Lesson lesson1 = new Lesson(LocalDate.now());
		Lesson lesson2 = new Lesson(LocalDate.now().plusDays(1));
		Lesson lesson3 = new Lesson(LocalDate.now().plusDays(2));
		Lesson lesson4 = new Lesson(LocalDate.now().plusDays(10));
		Lesson lesson5 = new Lesson(LocalDate.now().plusDays(5));
		
		aCourse.addTeacher(teacher1);
		
		aCourse.addStudent(student1);
		aCourse.addStudent(student2);
		aCourse.addStudent(student3);
//		aCourse.addStudent(student4);
//		aCourse.addStudent(student5);
		
		aCourse.addLesson(lesson1);
		aCourse.addLesson(lesson2);
		aCourse.addLesson(lesson3);
		
		lesson1.setAttendance(student1);
		lesson2.setAttendance(student2);
		lesson3.setAttendance(student1);
		lesson3.setAttendance(student2);
		
		
		otherCourse.addTeacher(teacher1);
		
		otherCourse.addStudent(student4);
		otherCourse.addStudent(student5);
		
		otherCourse.addLesson(lesson4);
		otherCourse.addLesson(lesson5);
		
		
		anotherCourse.addTeacher(teacher1);
		
		anotherCourse.addStudent(student4);
		anotherCourse.addStudent(student5);
		
		this.csService.createOrUpdateCourse(otherCourse);
		this.csService.createOrUpdateCourse(aCourse);
		this.csService.createOrUpdateCourse(anotherCourse);
	}
	
}
