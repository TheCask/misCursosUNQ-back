package ar.edu.unq.mis_cursos_unq.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unq.mis_cursos_unq.Subject;
import ar.edu.unq.mis_cursos_unq.User;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.services.SubjectService;
 
@RestController
@RequestMapping("/api")
public class SubjectController {
    
	@Autowired
    private SubjectService sbService;
 
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
    
    @GetMapping("/subject/{code}/coordinators")
    public ResponseEntity<List<User>> getSubjectCoordinatorsByCode(@PathVariable("code") String code) throws RecordNotFoundException {
        List<User> coordinators = sbService.getSubjectCoordinatorsByCode(code);
        return new ResponseEntity<List<User>>(coordinators, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/subject/{code}/courseQty")
    public ResponseEntity<String> getSubjectCourseQtyByCode(@PathVariable("code") String code) throws RecordNotFoundException {
        String courseQty = sbService.getCourseQtyByCode(code).toString();
        return new ResponseEntity<String>(courseQty, new HttpHeaders(), HttpStatus.OK);
    }
 
 
    @PostMapping("/subject")
    public  @ResponseBody ResponseEntity<Subject> createOrUpdateSubject(@RequestBody Subject subject) throws SubjectException {
        Subject updated = sbService.createOrUpdateSubject(subject);
        return new ResponseEntity<Subject>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PostMapping("/subject/{code}/coordinators")
    public  @ResponseBody ResponseEntity<List<User>> createOrUpdateSubjectCoordinators(@RequestBody List<User> coordinators, @PathVariable("code") String code) throws SubjectException, RecordNotFoundException {
        List<User> updatedCoordinators = sbService.createOrUpdateSubjectCoordinators(code, coordinators);
        return new ResponseEntity<List<User>>(updatedCoordinators, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/subject/{code}")
    public ResponseEntity<String> deleteSubjectByCode(@PathVariable("code") String code) throws RecordNotFoundException, SubjectException {
        sbService.deleteSubjectByCode(code);
        return new ResponseEntity<String>("Subject " + code + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);
    }
 
}