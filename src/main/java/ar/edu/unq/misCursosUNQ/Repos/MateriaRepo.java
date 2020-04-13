package ar.edu.unq.misCursosUNQ.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.misCursosUNQ.Materia;

@Repository
public interface MateriaRepo extends JpaRepository<Materia, Long> {
	/* By this simple extension, MateriaRepository inherits several methods 
	 * for working with Materia persistence, including methods for saving, deleting, and finding Materia entities.
	 * Along with default provided methods, we can add our own custom methods and queries to this interface. */
}
