//package ar.edu.unq.misCursosUNQ.Repos;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import ar.edu.unq.misCursosUNQ.Subject;
//
////@Repository
//public interface SubjectRepo extends JpaRepository<Subject, String> {
//
//	/* By this simple extension, MateriaRepo inherits several methods 
//	 * for working with Materia persistence, including methods for saving, deleting, and finding Subject entities.
//	 * Along with default provided methods, we can add our own custom methods and queries to this interface. */
//	
//	Optional<Subject> findByCode(String code);
//
//	void deleteByCode(String code);
//}