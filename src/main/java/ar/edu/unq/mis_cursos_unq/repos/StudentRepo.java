package ar.edu.unq.mis_cursos_unq.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.mis_cursos_unq.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

	Optional<Student> findByFileNumber(Integer fileNumber);
	
	void deleteByFileNumber(Integer fileNumber);
}