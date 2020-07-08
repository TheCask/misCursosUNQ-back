package ar.edu.unq.mis_cursos_unq.repos;

import java.time.LocalDate;

import javax.persistence.*;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.unq.mis_cursos_unq.Course;
import ar.edu.unq.mis_cursos_unq.Evaluation;
import ar.edu.unq.mis_cursos_unq.Lesson;
import ar.edu.unq.mis_cursos_unq.Student;
import ar.edu.unq.mis_cursos_unq.Subject;
import ar.edu.unq.mis_cursos_unq.services.CourseService;
import ar.edu.unq.mis_cursos_unq.services.SubjectService;

@Component
public class DataBaseLoader implements CommandLineRunner  {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CourseService csService;
	
	@Autowired
	private SubjectService sbService;
	
	@Autowired
	public DataBaseLoader() {
		// Empty Body
	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		try { fullTextEntityManager.createIndexer().startAndWait(); } 
		catch (InterruptedException e) {
			System.out.println("Error occured trying to build Hibernate Search indexes " + e.toString());
		}
		
		Subject lea = new Subject("Lectura y Escritura Académica", "80000-CyT1y2", "LEA");
		Subject epyl = new Subject("Elementos de Programación y Lógica", "80005-CyT2", "EPYL");
		Subject icfq = new Subject("Introducción al Conocimiento de la Física y la Química", "80004-CyT1", "ICFYQ");
		
		this.sbService.createOrUpdateSubject(lea);
		this.sbService.createOrUpdateSubject(epyl);
		this.sbService.createOrUpdateSubject(icfq);
		
		Course aCourse = new Course("17", lea, 2020, "1c");
		aCourse.setCourseIsOpen(false);
		aCourse.setCourseShift("Noche");
		
		Course otherCourse = new Course("B3", epyl, 2019, "3t");
		otherCourse.setCourseIsOpen(true);
		otherCourse.setCourseShift("Mañana");
		
		Course anotherCourse = new Course("SF12", icfq, 2021, "2C");
		anotherCourse.setCourseIsOpen(true);
		anotherCourse.setCourseShift("Tarde");
		
//		User teacher1 = new User("El Profe", "Romero", "el_profe@unq.edu.ar", 12658953);
//		User teacher2 = new User("Eugenio", "Calcena", "eugeniocalcena@gmail.com", 28860590);
		
		Student student1 = new Student("Julian", "Asange", 28123123, "julianaa@gmail.com",14111);
		Student student2 = new Student("Harry", "Potter", 29123456, "harry.potter@gmail.com",14222);
		Student student3 = new Student("Regina", "Falange",  33333333, "regina@gmail.com",14333);
		Student student4 = new Student("Aurelio", "Gómez",   44444444, "aurelio_gomez@gmail.com",14444);
		Student student5 = new Student("Nahuel", "Huapi",    55555555, "huapi.n@gmail.com",14555);
		
		Evaluation anEvaluation = new Evaluation("Primer Parcial");
		anEvaluation.setStudentNote(student1, 7f);
		
		Lesson lesson1 = new Lesson(LocalDate.now());
		Lesson lesson2 = new Lesson(LocalDate.now().plusDays(1));
		Lesson lesson3 = new Lesson(LocalDate.now().plusDays(2));
		Lesson lesson4 = new Lesson(LocalDate.now().plusDays(10));
		Lesson lesson5 = new Lesson(LocalDate.now().plusDays(5));
		
//		aCourse.addTeacher(teacher2);
		
		aCourse.addStudent(student1);
		aCourse.addStudent(student2);
		aCourse.addStudent(student3);
		aCourse.addStudent(student4);
		aCourse.addStudent(student5);
		
		aCourse.addLesson(lesson1);
		aCourse.addLesson(lesson2);
		aCourse.addLesson(lesson3);
		
		aCourse.addEvaluation(anEvaluation);
		
		lesson1.setAttendance(student1);
		lesson2.setAttendance(student2);
		lesson3.setAttendance(student1);
		lesson3.setAttendance(student2);
		
		
//		otherCourse.addTeacher(teacher1);
		
		otherCourse.addStudent(student4);
		otherCourse.addStudent(student5);
		
		otherCourse.addLesson(lesson4);
		otherCourse.addLesson(lesson5);
		
		
//		anotherCourse.addTeacher(teacher1);
		
		anotherCourse.addStudent(student4);
		anotherCourse.addStudent(student5);
		
		this.csService.createOrUpdateCourse(otherCourse);
		this.csService.createOrUpdateCourse(aCourse);
		this.csService.createOrUpdateCourse(anotherCourse);
	}
	
}
