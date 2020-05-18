package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;
import ar.edu.unq.misCursosUNQ.Repos.LessonRepo;
 
@Service
public class LessonService {
     
    @Autowired
	LessonRepo lnRepo;
    
    @Autowired
	CourseRepo csRepo;
    
    @Autowired
	CourseService csService;
     
    public List<Lesson> getLessons() {
        List<Lesson> aList = lnRepo.findAll();
         
        if(aList.size() > 0) { return aList; } 
        else { return new ArrayList<Lesson>(); }
    }
     
    public Lesson getLessonById(Long id) throws RecordNotFoundException {
        Optional<Lesson> optional = lnRepo.findById(id);
         
        if(optional.isPresent()) { return optional.get(); } 
        else { throw new RecordNotFoundException("Lesson record does not exist for given id"); }
    }
    
    @Transactional
    public Lesson createOrUpdateLesson(Lesson entity) throws RecordNotFoundException {
        
    	// Update lesson (Note: lesson course is not meant to be updated)
    	if (entity.getLessonId() != null) {
    		
	    	Optional<Lesson> optEntity = lnRepo.findById(entity.getLessonId());
	
	    	if(optEntity.isPresent()) { // Lesson exists in db
	    		
	    		// TODO Check what happens with attendantStudents
				// this lists updates automagically when saving lesson entity by cascade anotations?
				// or has to be manually updated here in this service (more probably)?
	    		Lesson newEntity = optEntity.get();
	    			
	    		return lnRepo.save(newEntity);
	    	}
	    	throw new RecordNotFoundException("Lesson record does not exist for given id");
    	}
    	// New lesson
	    return lnRepo.save(entity);
    }	
    
    @Transactional
    public void deleteLessonById(Long id) throws RecordNotFoundException {
        
    	Optional<Lesson> optEntity = lnRepo.findById(id);
         
        if(optEntity.isPresent()) { 
        	
        	Lesson lesson = optEntity.get();
        	Course lessonCourse = lesson.getCourse();
        	
        	lessonCourse.removeLesson(lesson); // this also removes all attendance to the lesson

        	lnRepo.deleteById(lesson.getLessonId());
        	csService.createOrUpdateCourse(lessonCourse);
        } 
        else { throw new RecordNotFoundException("Lesson record does not exist for given id"); }
    } 
}