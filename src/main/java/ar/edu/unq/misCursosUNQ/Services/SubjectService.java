package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.SubjectRepo;
 
@Service
public class SubjectService {
     
    @Autowired
    SubjectRepo repository;
     
    public List<Subject> getSubjects() {
        List<Subject> subjectList = repository.findAll();
         
        if(subjectList.size() > 0) { return subjectList; } 
        else { return new ArrayList<Subject>(); }
    }
     
    public Subject getSubjectById(Long id) throws RecordNotFoundException {
        Optional<Subject> subject = repository.findById(id);
         
        if(subject.isPresent()) { return subject.get(); } 
        else { throw new RecordNotFoundException("Subject record not exist for given id"); }
    }
     
    public Subject createOrUpdateSubject(Subject entity) throws RecordNotFoundException {
        Optional<Subject> subject = repository.findById(entity.getId());
         
        if(subject.isPresent()) {
            Subject newEntity = subject.get();
            
            newEntity.setName(entity.getName());
 
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } 
        else {
            entity = repository.save(entity); 
            return entity;
        }
    } 
     
    public void deleteSubjectById(Long id) throws RecordNotFoundException {
        Optional<Subject> subject = repository.findById(id);
         
        if(subject.isPresent()) { repository.deleteById(id); } 
        else { throw new RecordNotFoundException("Subject record not exist for given id"); }
    } 
}