package ar.edu.unq.mis_cursos_unq.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.mis_cursos_unq.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
	/* By this simple extension, MateriaRepo inherits several methods 
	 * for working with Materia persistence, including methods for saving, deleting, and finding Materia entities.
	 * Along with default provided methods, we can add our own custom methods and queries to this interface. */
	 
	
	//Course findById(@Param("id") Integer id);
}