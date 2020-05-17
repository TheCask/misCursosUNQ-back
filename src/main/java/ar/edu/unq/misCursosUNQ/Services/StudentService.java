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
	StudentRepo repository;
     
    public List<Student> getStudents() {
        List<Student> aList = repository.findAll();
         
        if(aList.size() > 0) { return aList; } 
        else { return new ArrayList<Student>(); }
    }
     
    public Student getStudentById(Integer id) throws RecordNotFoundException {
        Optional<Student> optional = repository.findById(id);
         
        if(optional.isPresent()) { return optional.get(); } 
        else { throw new RecordNotFoundException("Student record does not exist for given id"); }
    }
    
    @Transactional
    public Student createOrUpdateStudent(Student entity) throws RecordNotFoundException {
        
    	if (entity.getFileNumber() != null) {

			Optional<Student> optEntity = repository.findById(entity.getFileNumber());

			if(optEntity.isPresent()) {

				Student newEntity = optEntity.get();

				newEntity.setPersonalData(entity.getPersonalData());

				return repository.save(newEntity);
			} 
		}
		return repository.save(entity);
    }
    
    @Transactional 
    public void deleteStudentById(Integer id) throws RecordNotFoundException {
        Optional<Student> optEntity = repository.findById(id);
         
        if(optEntity.isPresent()) {
        	
        	Student st = optEntity.get();
        	List<Lesson> lessons = new ArrayList<Lesson>(st.getAttendedLessons());
        	List<Course> courses = new ArrayList<Course>(st.getTakenCourses());
        	
        	courses.forEach(cs -> cs.removeStudent(st));
        	lessons.forEach(ln -> ln.removeAttendance(st));
        	
        	repository.deleteById(id); 
        	
        } 
        else { throw new RecordNotFoundException("Student record does not exist for given id"); }
    } 
}