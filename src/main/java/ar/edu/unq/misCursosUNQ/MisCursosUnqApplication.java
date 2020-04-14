package ar.edu.unq.misCursosUNQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.unq.misCursosUNQ.Repos.MateriaRepo;
import ar.edu.unq.misCursosUNQ.Services.MateriaService;

@SpringBootApplication
public class MisCursosUnqApplication implements CommandLineRunner {

	@Autowired
	MateriaService service;
	
	@Autowired
	MateriaRepo repo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(MisCursosUnqApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception { 
		
		Materia lea = new Materia("LEA");
		Materia icfyq = new Materia("ICFyQ");
		Materia mate = new Materia("MATE");
		Materia epyl = new Materia("EPYL");
		
		repo.save(lea);
		repo.save(icfyq);
		repo.save(mate);
		repo.save(epyl);
 
        logger.info("Materias: -> {}", repo.findAll().toString());
    }
}