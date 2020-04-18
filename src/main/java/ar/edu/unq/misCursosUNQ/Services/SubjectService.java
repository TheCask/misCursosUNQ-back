//package ar.edu.unq.misCursosUNQ.Services;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import ar.edu.unq.misCursosUNQ.Subject;
//import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
//import ar.edu.unq.misCursosUNQ.Repos.SubjectRepo;
// 
////@Service
//public class SubjectService {
//     
//    //@Autowired
//    SubjectRepo repository;
//     
//    public List<Subject> getSubjects() {
//        List<Subject> subjectList = repository.findAll();
//         
//        if(subjectList.size() > 0) { return subjectList; } 
//        else { return new ArrayList<Subject>(); }
//    }
//     
//    public Subject getSubjectByCode(String code) throws RecordNotFoundException {
//        Optional<Subject> subject = repository.findByCode(code);
//         
//        if(subject.isPresent()) { return subject.get(); } 
//        else { throw new RecordNotFoundException("Subject record not exist for given code"); }
//    }
//     
//    public Subject createOrUpdateSubject(Subject entity) throws RecordNotFoundException {
//        
//    	Optional<Subject> subject = repository.findByCode(entity.getCode());
//
//    	if(subject.isPresent()) {
//    		Subject newEntity = subject.get();
//
//    		newEntity.setName(entity.getName());	
//    		newEntity.setAcronym(entity.getAcronym());
//    		newEntity.setProgramURL(entity.getProgramURL());
//    		newEntity.setCoordinators(entity.getCoordinators());
//    		newEntity.setCourses(entity.getCourses());
//
//    		newEntity = repository.save(newEntity);
//
//    		return newEntity;
//    	} 
//    	else { return repository.save(entity); }
//	        
//    } 
//     
//    public void deleteSubjectByCode(String code) throws RecordNotFoundException {
//        Optional<Subject> subject = repository.findByCode(code);
//         
//        if(subject.isPresent()) { repository.deleteByCode(code); } 
//        else { throw new RecordNotFoundException("Subject record not exist for given id"); }
//    } 
//}