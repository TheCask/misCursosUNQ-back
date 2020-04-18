package ar.edu.unq.misCursosUNQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import ar.edu.unq.misCursosUNQ.Services.SubjectService;
//import ar.edu.unq.misCursosUNQ.Services.UserService;

@SpringBootApplication
public class MisCursosUnqApplication implements CommandLineRunner {

	//@Autowired
	//SubjectService subjectService;
	
	//@Autowired
	//UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(MisCursosUnqApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
		
		Student student1 = new Student("Student1", "Bla", 123123, "s1@gmail.com",14555);
		Student student2 = new Student("Student2", "Ble", 123456, "s2@gmail.com",14666);
		
		
		
		/*
		Subject lea = new Subject("Lectura y Escritura Académica", "80000-CYT1y2", "LEA");
		Subject icfyq = new Subject("Introducción al Conocimiento de la Física y la Química", "80004-CYT1", "ICFYQ");
		Subject mate = new Subject("Matemática", "80003-CYT1y2", "MATE");
		Subject epyl = new Subject("Elementos de Programación y lógica", "80005-CYT2", "EPYL");
		
		User admin = new User("Eugenio", "Calcena", "eugeniocalcena@gmail.com", 28860590);
		User admin2 = new User("Elias", "Filipponi", "eliasfilipponi@gmail.com", 29085595);
		
		userService.createOrUpdateUser(admin);
		subjectService.createOrUpdateSubject(lea);
		admin.getCoordinatedSubjects().add(lea);
		userService.createOrUpdateUser(admin);
		
		userService.createOrUpdateUser(admin2);
		subjectService.createOrUpdateSubject(icfyq);
		icfyq.getCoordinators().add(admin2);
		admin2.getCoordinatedSubjects().add(icfyq);
		userService.createOrUpdateUser(admin2);
		subjectService.createOrUpdateSubject(icfyq);
		
        logger.info("Materias: -> {}", subjectService.getSubjects().toString());
        logger.info("Users: -> {}", userService.getUsers().toString());
       */
    }
}