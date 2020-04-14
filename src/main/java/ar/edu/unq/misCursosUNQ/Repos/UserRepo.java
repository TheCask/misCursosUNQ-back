package ar.edu.unq.misCursosUNQ.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.misCursosUNQ.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	/* By this simple extension, MateriaRepo inherits several methods 
	 * for working with Materia persistence, including methods for saving, deleting, and finding Materia entities.
	 * Along with default provided methods, we can add our own custom methods and queries to this interface. */
}