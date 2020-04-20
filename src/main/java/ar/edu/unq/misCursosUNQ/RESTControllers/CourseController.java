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

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Services.CourseService;
 
@RestController
@RequestMapping("/courses")
public class CourseController {
    
	@Autowired
    CourseService service;
 
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> list = service.getCourses();
        return new ResponseEntity<List<Course>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
        Course entity = service.getCourseById(courseId);
        return new ResponseEntity<Course>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public @ResponseBody ResponseEntity<Course> createOrUpdateCourse(@RequestBody Course course) throws RecordNotFoundException {
        Course updated = service.createOrUpdateCourse(course);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{courseId}")
    public HttpStatus deleteCourseById(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
        service.deleteCourseById(courseId);
        return HttpStatus.FORBIDDEN;
    }
}