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

import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Services.LessonService;
 
@RestController
@RequestMapping("/lessons")
public class LessonController {
    
	@Autowired
    LessonService service;
 
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> list = service.getLessons();
        return new ResponseEntity<List<Lesson>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Lesson entity = service.getLessonById(id);
        return new ResponseEntity<Lesson>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Lesson> createOrUpdateLesson(Lesson lesson) throws RecordNotFoundException {
        Lesson updated = service.createOrUpdateLesson(lesson);
        return new ResponseEntity<Lesson>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteLessonById(@PathVariable("id") Long id) throws RecordNotFoundException {
        service.deleteLessonById(id);
        return HttpStatus.FORBIDDEN;
    }
 
}