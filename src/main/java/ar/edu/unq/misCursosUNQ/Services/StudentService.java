package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.PersonalDataRepo;
import ar.edu.unq.misCursosUNQ.Repos.StudentRepo;
 
@Service
public class StudentService {
     
    @Autowired
	StudentRepo repository;
    
    @Autowired
	PersonalDataRepo subRepo;
     
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
     
    public Student createOrUpdateStudent(Student entity) throws RecordNotFoundException {
        
    	Optional<Student> optEntity = repository.findById(entity.getFileNumber());

    	if(optEntity.isPresent()) {
    		
    		Student newEntity = optEntity.get();

    		newEntity.setPersonalData(entity.getPersonalData());	
    		newEntity.setTakenCourses(entity.getTakenCourses());	

    		newEntity = repository.save(newEntity);

    		return newEntity;
    	} 
    	else { 
    		subRepo.save(entity.getPersonalData());
    		return repository.save(entity);
    	}
    } 
     
    public void deleteStudentById(Integer id) throws RecordNotFoundException {
        Optional<Student> optEntity = repository.findById(id);
         
        if(optEntity.isPresent()) { repository.deleteById(id); } 
        else { throw new RecordNotFoundException("Student record does not exist for given id"); }
    } 
}