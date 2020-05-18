package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.StudentRepo;
 
@Service
public class StudentService {
     
    @Autowired
	StudentRepo stRepo;
     
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

				// TODO Check what happens with careers, takenCourses and attendedLessons
				// this lists updates automagically when saving student entity by cascade anotations?
				// or has to be manually updated here in this service (more probably)?
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
        
    	Optional<Student> optEntity = stRepo.findById(fileNumber);
         
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
}