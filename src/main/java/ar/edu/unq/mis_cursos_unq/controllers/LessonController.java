package ar.edu.unq.mis_cursos_unq.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unq.mis_cursos_unq.Lesson;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.services.LessonService;
 
@RestController
@RequestMapping("/api")
public class LessonController {
    
	@Autowired
    private LessonService lnService;
 
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
    public ResponseEntity<String> deleteLessonById(@PathVariable("lessonId") Long lessonId) throws RecordNotFoundException, SubjectException, SeasonException {
        lnService.deleteLessonById(lessonId);
        return new ResponseEntity<String>("Lesson " + lessonId + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);   
    }
}