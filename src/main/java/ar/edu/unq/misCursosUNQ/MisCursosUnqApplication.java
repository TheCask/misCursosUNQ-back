package ar.edu.unq.misCursosUNQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.unq.misCursosUNQ.Services.CourseService;
import ar.edu.unq.misCursosUNQ.Services.LessonService;
import ar.edu.unq.misCursosUNQ.Services.StudentService;

//import ar.edu.unq.misCursosUNQ.Services.SubjectService;
//import ar.edu.unq.misCursosUNQ.Services.UserService;

@SpringBootApplication
public class MisCursosUnqApplication implements CommandLineRunner {

	//@Autowired
	//SubjectService subjectService;
	
	//@Autowired
	//UserService userService;
	
	@Autowired
	private CourseService csService;

	@Autowired
	private StudentService stService;
	
	@Autowired
	private LessonService lnService;
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(MisCursosUnqApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
		
		
//		csService.createOrUpdateCourse(course1);
//		
//		stService.createOrUpdateStudent(student1);
//		stService.createOrUpdateStudent(student2);
//		
//		course1.addLesson(lesson1);
//		course1.addLesson(lesson2);
//		course1.addLesson(lesson3);
//		
//		lnService.createOrUpdateLesson(lesson1);
//		lnService.createOrUpdateLesson(lesson2);
//		lnService.createOrUpdateLesson(lesson3);
//		
//		course1.addStudent(student1);
//		course1.addStudent(student2);
//		
//		lesson1.setAttendance(student1);
//		lesson1.setAttendance(student2);
//		
//		lnService.createOrUpdateLesson(lesson1);
//		lnService.createOrUpdateLesson(lesson2);
//		lnService.createOrUpdateLesson(lesson3);
//		stService.createOrUpdateStudent(student1);
//		stService.createOrUpdateStudent(student2);
//		csService.createOrUpdateCourse(course1);
		
		/*
        logger.info("Materias: -> {}", subjectService.getSubjects().toString());
        logger.info("Users: -> {}", userService.getUsers().toString());
       */
    }
}