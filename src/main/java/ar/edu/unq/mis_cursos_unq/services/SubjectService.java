package ar.edu.unq.mis_cursos_unq.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.mis_cursos_unq.Subject;
import ar.edu.unq.mis_cursos_unq.User;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.repos.SubjectRepo;
 
@Service
public class SubjectService {
     
    @Autowired
    private SubjectRepo sbRepo;
    
    @Autowired
    private CourseService csService;
    
    @Autowired
    private UserService usService;
     
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
    
    public List<User> getSubjectCoordinatorsByCode(String code) throws RecordNotFoundException {
    	this.getSubjectByCode(code);
    	return usService.getCoordinatorsByCode(code);
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

    @Transactional
	public List<User> createOrUpdateSubjectCoordinators(String subjectCode, List<User> newCoordinators) throws RecordNotFoundException {
		List<User> oldCoordinators = this.getSubjectCoordinatorsByCode(subjectCode);
		Subject subject = this.getSubjectByCode(subjectCode);
    	
		// Delete coordinators
		oldCoordinators.removeAll(newCoordinators); // this changes oldCoordinators leaving only users that are no longer coordinators 
		oldCoordinators.forEach(us -> us.unAssignSubject(subject));
		
		// Add coordinators
		newCoordinators.removeAll(oldCoordinators); // this changes newCoordinators leaving only users thar are new coordinators
		for (User us : newCoordinators) {
			try { 
				User usToUpdate = usService.getUserById(us.getUserId());
				usToUpdate.assignSubject(subject);
			}
			catch (RecordNotFoundException e) { e.printStackTrace(); }
		}
		
		return this.getSubjectCoordinatorsByCode(subjectCode);
	}
}