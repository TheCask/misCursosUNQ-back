package ar.edu.unq.misCursosUNQ.RESTControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Services.SubjectService;
 
@RestController
@RequestMapping("/subjects")
public class SubjectController {
    
	@Autowired
    SubjectService service;
 
    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> list = service.getSubjects();
        return new ResponseEntity<List<Subject>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Subject entity = service.getSubjectById(id);
        return new ResponseEntity<Subject>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Subject> createOrUpdateSubject(Subject subject) throws RecordNotFoundException {
        Subject updated = service.createOrUpdateSubject(subject);
        return new ResponseEntity<Subject>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteSubjectById(@PathVariable("id") Long id) throws RecordNotFoundException {
        service.deleteSubjectById(id);
        return HttpStatus.FORBIDDEN;
    }
 
}