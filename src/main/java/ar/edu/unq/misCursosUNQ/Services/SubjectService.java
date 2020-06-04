package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Exceptions.SubjectException;
import ar.edu.unq.misCursosUNQ.Repos.SubjectRepo;
 
@Service
public class SubjectService {
     
    @Autowired
    SubjectRepo sbRepo;
    
    @Autowired
    CourseService csService;
     
    public List<Subject> getSubjects() {
        List<Subject> subjectList = sbRepo.findAll();
         
        if(subjectList.size() > 0) { return subjectList; } 
        else { return new ArrayList<Subject>(); }
    }
     
    public Subject getSubjectByCode(String code) throws RecordNotFoundException {
        Optional<Subject> subject = sbRepo.findByCode(code);
         
        if(subject.isPresent()) { return subject.get(); } 
        else { throw new RecordNotFoundException("Subject record not exist for given code"); }
    }
    
    @Transactional
    public Subject createOrUpdateSubject(Subject entity) throws SubjectException {
        
    	if (entity.getCode() != null) {
    	
	    	Optional<Subject> subject = sbRepo.findByCode(entity.getCode());
	
	    	// Update subject
	    	if(subject.isPresent()) {
	    		Subject newEntity = subject.get();
	
	    		newEntity.setName(entity.getName());	
	    		newEntity.setAcronym(entity.getAcronym());
	    		newEntity.setProgramURL(entity.getProgramURL());
	    
	    		return sbRepo.save(newEntity);
	    	}
	    	// Create subject
	    	return sbRepo.save(entity);
    	}
    	// Subject code null
    	throw new SubjectException("Subject code (PK) is null");
    } 
    
    @Transactional
    public void deleteSubjectByCode(String code) throws RecordNotFoundException, SubjectException {
        Optional<Subject> subject = sbRepo.findByCode(code);
        
        // Subject can/should not cascade delete operation to courses
        if(subject.isPresent()) {
        	if (!csService.getCourses().stream().anyMatch(cs -> cs.getSubject().equals(subject.get()))) {
        		sbRepo.deleteByCode(code);
        	}
        	else { throw new SubjectException("Forbidden subject delete, there are courses associated to this subject");}
        } 
        else { throw new RecordNotFoundException("Subject record not exist for given code"); }
    } 
}