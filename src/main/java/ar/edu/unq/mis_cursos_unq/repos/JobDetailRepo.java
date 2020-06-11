package ar.edu.unq.mis_cursos_unq.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.mis_cursos_unq.JobDetail;

@Repository
public interface JobDetailRepo extends JpaRepository<JobDetail, Integer> {

	/* By this simple extension, MateriaRepo inherits several methods 
	 * for working with Materia persistence, including methods for saving, deleting, and finding User entities.
	 * Along with default provided methods, we can add our own custom methods and queries to this interface. */
}