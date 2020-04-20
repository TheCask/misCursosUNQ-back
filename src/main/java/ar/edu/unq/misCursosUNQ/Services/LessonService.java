package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.LessonRepo;
 
@Service
public class LessonService {
     
    @Autowired
	LessonRepo repository;
     
    public List<Lesson> getLessons() {
        List<Lesson> aList = repository.findAll();
         
        if(aList.size() > 0) { return aList; } 
        else { return new ArrayList<Lesson>(); }
    }
     
    public Lesson getLessonById(Long id) throws RecordNotFoundException {
        Optional<Lesson> optional = repository.findById(id);
         
        if(optional.isPresent()) { return optional.get(); } 
        else { throw new RecordNotFoundException("Lesson record does not exist for given id"); }
    }
     
    public Lesson createOrUpdateLesson(Lesson entity) throws RecordNotFoundException {
        
    	
    	
    	/*
    	Optional<Lesson> optEntity = repository.findById(entity.getLessonId());

    	if(optEntity.isPresent()) {
    		
    		Lesson newEntity = optEntity.get();

    		newEntity.setAttendantStudents(entity.getAttendantStudents());	

    		newEntity = repository.save(newEntity);

    		return newEntity;
    	} 
    	else { return repository.save(entity); }
    	*/
    	///////////////
    	if (entity.getLessonId() != null) {
    		
	    	Optional<Lesson> optEntity = repository.findById(entity.getLessonId());
	
	    	if(optEntity.isPresent()) {
	    		
	    		Lesson newEntity = optEntity.get();
	
	    		newEntity.setAttendantStudents(entity.getAttendantStudents());	
	
	    		newEntity = repository.save(newEntity);
	
	    		return newEntity;
	    	} 
    	}
    	return repository.save(entity);
    	
    	/////////////
    	/*
    	if (entity.getCourseId() == null) {	return repository.save(entity); }
		
		else {
			Optional<Course> course; course = repository.findById(entity.getCourseId());

			Course newEntity = course.get();
			
			if(course.isPresent()) {
				
				newEntity.setName(entity.getName());	
				newEntity.setCode(entity.getCode());
				newEntity.setCourseIsOpen(entity.getCourseIsOpen());
				newEntity.setCourseShift(entity.getCourseShift());
				newEntity.setLessons(entity.getLessons());
				newEntity.setStudents(entity.getStudents());
	
				newEntity = repository.save(newEntity);
			}
			else { newEntity = repository.save(entity); }
			
			return newEntity;
		}
    	
    	
	      */  
    } 
     
    public void deleteLessonById(Long id) throws RecordNotFoundException {
        Optional<Lesson> optEntity = repository.findById(id);
         
        if(optEntity.isPresent()) { repository.deleteById(id); } 
        else { throw new RecordNotFoundException("Lesson record does not exist for given id"); }
    } 
}