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

import ar.edu.unq.misCursosUNQ.Lesson;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Services.LessonService;
 
@RestController
@RequestMapping("/api")
public class LessonController {
    
	@Autowired
    LessonService lnService;
 
    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> list = lnService.getLessons();
        return new ResponseEntity<List<Lesson>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable("lessonId") Long lessonId) throws RecordNotFoundException {
        Lesson entity = lnService.getLessonById(lessonId);
        return new ResponseEntity<Lesson>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/lesson")
    public @ResponseBody ResponseEntity<Lesson> createOrUpdateLesson(@RequestBody Lesson lesson) throws RecordNotFoundException {
    	Lesson updated = lnService.createOrUpdateLesson(lesson);
        return new ResponseEntity<Lesson>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/lesson/{lessonId}")
    public ResponseEntity<String> deleteLessonById(@PathVariable("lessonId") Long lessonId) throws RecordNotFoundException {
        lnService.deleteLessonById(lessonId);
        return new ResponseEntity<String>("Lesson " + lessonId + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);   
    }
}