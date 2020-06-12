package ar.edu.unq.mis_cursos_unq.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.mis_cursos_unq.Course;
import ar.edu.unq.mis_cursos_unq.Lesson;
import ar.edu.unq.mis_cursos_unq.Student;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.repos.StudentDAO;
import ar.edu.unq.mis_cursos_unq.repos.StudentRepo;
 
@Service
public class StudentService {
	
    @Autowired
    private StudentRepo stRepo;
    
    @Autowired
    private StudentDAO stDAO;
     
    public List<Student> getStudents() {
        List<Student> aList = stRepo.findAll();
         
        if(aList.size() > 0) { return aList; } 
        else { return new ArrayList<Student>(); }
    }
     
    public Student getStudentByFileNumber(Integer fileNumber) throws RecordNotFoundException {
        Optional<Student> optional = stRepo.findByFileNumber(fileNumber);
         
        if(optional.isPresent()) { return optional.get(); } 
        else { throw new RecordNotFoundException("Student record does not exist for given file number"); }
    }
    
    @Transactional
    public Student createOrUpdateStudent(Student entity) throws RecordNotFoundException {
        
    	if (entity.getFileNumber() != null) {

			Optional<Student> optEntity = stRepo.findByFileNumber(entity.getFileNumber());

			if(optEntity.isPresent()) {

				Student newEntity = optEntity.get();

				// TODO Update careers, takenCourses and attendedLessons if needed
				newEntity.setPersonalData(entity.getPersonalData());
				
				return stRepo.save(newEntity);
			}
			// Create a new student
			return stRepo.save(entity);
		}
    	throw new RecordNotFoundException("Student record does not exist for given file number");
    }

	@Transactional 
    public void deleteStudentByFileNumber(Integer fileNumber) throws RecordNotFoundException {
        
    	Optional<Student> optEntity = stRepo.findByFileNumber(fileNumber);
         
        if(optEntity.isPresent()) {
        	
        	Student st = optEntity.get();
        	
        	// New array list to avoid concurrente modification
        	List<Lesson> stLessons = new ArrayList<Lesson>(st.getAttendedLessons());
        	List<Course> stCourses = new ArrayList<Course>(st.getTakenCourses());
        	
        	// Delete an student implies delete all asociations with courses taken and lessons attended
        	stCourses.forEach(cs -> cs.removeStudent(st));
        	stLessons.forEach(ln -> ln.removeAttendance(st));
        	
        	stRepo.deleteByFileNumber(fileNumber);
        	
        } 
        else { throw new RecordNotFoundException("Student record does not exist for given file number"); }
    }
	
	@Transactional
	public Integer searchStudentsResultsCount(String searchText) {
		return stDAO.searchStudentsTotalCount(searchText);
	}

	public Integer searchStudentsPagesCount(String searchText, int studentPerPage) {
		Integer studentCount = this.searchStudentsResultsCount(searchText);
		
		return (Integer) Math.floorDiv(studentCount, studentPerPage) + 1;
	}
	
	@Transactional
	public List<Student> searchStudents(String searchText, Integer pageNo, int studentPerPage) {
		return stDAO.searchStudents(searchText, pageNo, studentPerPage);
	}
}