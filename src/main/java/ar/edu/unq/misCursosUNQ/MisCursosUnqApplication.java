package ar.edu.unq.misCursosUNQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.unq.misCursosUNQ.Repos.SubjectRepo;
import ar.edu.unq.misCursosUNQ.Services.SubjectService;

@SpringBootApplication
public class MisCursosUnqApplication implements CommandLineRunner {

	@Autowired
	SubjectService service;
	
	@Autowired
	SubjectRepo repo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(MisCursosUnqApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception { 
		
		Subject lea = new Subject("LEA");
		Subject icfyq = new Subject("ICFyQ");
		Subject mate = new Subject("MATE");
		Subject epyl = new Subject("EPYL");
		
		repo.save(lea);
		repo.save(icfyq);
		repo.save(mate);
		repo.save(epyl);
 
        logger.info("Materias: -> {}", repo.findAll().toString());
    }
}