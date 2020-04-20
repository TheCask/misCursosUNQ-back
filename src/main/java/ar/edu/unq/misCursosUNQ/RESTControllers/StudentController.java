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

import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Services.StudentService;
 
@RestController
@RequestMapping("/students")
public class StudentController {
    
	@Autowired
    StudentService service;
 
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> list = service.getStudents();
        return new ResponseEntity<List<Student>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{fileNumber}")
    public ResponseEntity<Student> getStudentById(@PathVariable("fileNumber") Integer fileNumber) throws RecordNotFoundException {
        Student entity = service.getStudentById(fileNumber);
        return new ResponseEntity<Student>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public @ResponseBody ResponseEntity<Student> createOrUpdateStudent(@RequestBody Student student) throws RecordNotFoundException {
    	Student updated = service.createOrUpdateStudent(student);
        return new ResponseEntity<Student>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{fileNumber}")
    public ResponseEntity<String> deleteStudentById(@PathVariable("fileNumber") Integer fileNumber) throws RecordNotFoundException {
        service.deleteStudentById(fileNumber);
        return new ResponseEntity<String>("Student " + fileNumber + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);
    }
}