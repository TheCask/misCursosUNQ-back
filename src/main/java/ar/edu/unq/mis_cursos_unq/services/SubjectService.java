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
    

	public Long getCourseQtyByCode(String code) throws RecordNotFoundException {
		return csService.getCourses().stream().filter(cs -> 
			cs.getSubject().getCode().equals(code)).count();
	}
    
    public List<User> getSubjectCoordinatorsByCode(String code) throws RecordNotFoundException {
    	this.getSubjectByCode(code);
    	return usService.getCoordinatorsByCode(code);
	}
    
    @Transactional
    public Subject updateSubject(Subject entity) throws SubjectException, RecordNotFoundException {
        
    	if (entity.getCode() != null) {
    	
	    	Subject dbSubject = this.getSubjectByCode(entity.getCode());
	
	    	// Update subject
    		dbSubject.setName(entity.getName());	
    		dbSubject.setAcronym(entity.getAcronym());
    		dbSubject.setProgramURL(entity.getProgramURL());
    
    		return sbRepo.save(dbSubject);
    	}
    	// Subject code null
    	throw new SubjectException("Subject code (PK) is null");
    }
    
    @Transactional
    public Subject createSubject(Subject entity) throws SubjectException {
        
    	if (entity.getCode() != null) {
    		if (!this.getSubjects().contains(entity)) { return sbRepo.save(entity); }
    		else { throw new SubjectException("Subject with same code already exist"); }
    	}
    	// Subject code null
    	throw new SubjectException("Subject code (PK) is null");
    }
    
    @Transactional
    public void deleteSubjectByCode(String code) throws RecordNotFoundException, SubjectException {
        Subject dbSubject = this.getSubjectByCode(code);
        
        // Subject can/should not cascade delete operation to courses
        if (!csService.getCourses().stream().anyMatch(cs -> cs.getSubject().equals(dbSubject))) {
        	sbRepo.deleteByCode(code);
        }
        else { throw new SubjectException("Forbidden subject delete, there are courses associated to this subject"); }
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