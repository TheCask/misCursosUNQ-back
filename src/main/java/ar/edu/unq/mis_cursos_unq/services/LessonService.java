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
import ar.edu.unq.mis_cursos_unq.exceptions.LessonException;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.repos.LessonRepo;
 
@Service
public class LessonService {
     
    @Autowired
    private LessonRepo lnRepo;
    
    @Autowired
    private CourseService csService;
    
    @Autowired
    private StudentService stService;
     
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
        
    	// Update lesson (note that lesson course is not meant to be updated)
    	if (entity.getLessonId() != null) {
    		
	    	Optional<Lesson> optEntity = lnRepo.findById(entity.getLessonId());
	
	    	if(optEntity.isPresent()) { // Lesson exists in db
	    		
	    		Lesson newEntity = optEntity.get();
	    		
	    		this.updateAttendance(newEntity, entity);
	    			
	    		return lnRepo.save(newEntity);
	    	}
	    	throw new RecordNotFoundException("Lesson record does not exist for given id");
    	}
    	// New lesson
	    return lnRepo.save(entity);
    }	
    
    @Transactional
    public void deleteLessonById(Long id) throws RecordNotFoundException, SeasonException, SubjectException {
        
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
    
    protected void updateAttendance(Lesson dbLesson, Lesson newDataLesson) {
    	dbLesson.removeAllAttendance();
		newDataLesson.getAttendantStudents().forEach(st -> {
			try {
				Student dbStudent = stService.getStudentByFileNumber(st.getFileNumber());
				dbLesson.setAttendance(dbStudent);
			} 
			catch (RecordNotFoundException | LessonException e) { e.printStackTrace(); }
		});
	}
}