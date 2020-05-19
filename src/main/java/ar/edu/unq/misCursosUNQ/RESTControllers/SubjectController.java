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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Exceptions.SubjectException;
import ar.edu.unq.misCursosUNQ.Services.SubjectService;
 
@RestController
@RequestMapping("/api")
public class SubjectController {
    
	@Autowired
    SubjectService sbService;
 
    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> list = sbService.getSubjects();
        return new ResponseEntity<List<Subject>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/subject/{code}")
    public ResponseEntity<Subject> getSubjectByCode(@PathVariable("code") String code) throws RecordNotFoundException {
        Subject entity = sbService.getSubjectByCode(code);
        return new ResponseEntity<Subject>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/subject")
    public  @ResponseBody ResponseEntity<Subject> createOrUpdateSubject(@RequestBody Subject subject) throws SubjectException {
        Subject updated = sbService.createOrUpdateSubject(subject);
        return new ResponseEntity<Subject>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/subject/{code}")
    public ResponseEntity<String> deleteSubjectByCode(@PathVariable("code") String code) throws RecordNotFoundException, SubjectException {
        sbService.deleteSubjectByCode(code);
        return new ResponseEntity<String>("Subject " + code + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);
    }
 
}