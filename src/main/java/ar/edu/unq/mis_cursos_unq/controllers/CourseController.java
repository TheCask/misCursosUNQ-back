package ar.edu.unq.mis_cursos_unq.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unq.mis_cursos_unq.Course;
import ar.edu.unq.mis_cursos_unq.Evaluation;
import ar.edu.unq.mis_cursos_unq.Lesson;
import ar.edu.unq.mis_cursos_unq.Student;
import ar.edu.unq.mis_cursos_unq.User;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.services.CourseService;
 
@RestController
@RequestMapping("/api")
public class CourseController {
    
	@Autowired
    private CourseService csService;
 
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> list = csService.getCourses();
        return new ResponseEntity<List<Course>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/course/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
        Course entity = csService.getCourseById(courseId);
        return new ResponseEntity<Course>(entity, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
    	Course entity = csService.getCourseById(courseId);
    	List<Student> list = entity.getStudents();
        return new ResponseEntity<List<Student>>(list, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/course/{courseId}/lessons")
    public ResponseEntity<List<Lesson>> getCourseLessons(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
    	Course entity = csService.getCourseById(courseId);
    	List<Lesson> list = entity.getLessons();        
        return new ResponseEntity<List<Lesson>>(list, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/course/{courseId}/teachers")
    public ResponseEntity<List<User>> getCourseTeachers(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
    	Course entity = csService.getCourseById(courseId);
    	List<User> list = entity.getTeachers();        
        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/course/{courseId}/evaluations")
    public ResponseEntity<List<Evaluation>> getCourseEvaluations(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
    	Course entity = csService.getCourseById(courseId);
    	List<Evaluation> list = entity.getEvaluations();        
        return new ResponseEntity<List<Evaluation>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/course")
    public @ResponseBody ResponseEntity<Course> createOrUpdateCourse(@RequestBody Course course) throws RecordNotFoundException, SeasonException, SubjectException {
        Course updated = csService.createOrUpdateCourse(course);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PostMapping("/course/{courseId}/newEvaluation")
    public @ResponseBody ResponseEntity<Course> addEvaluationToCourse(@PathVariable("courseId") Integer courseId, @RequestBody Evaluation evaluation) throws RecordNotFoundException {
    	Course updated = csService.addNewEvaluation(courseId, evaluation);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("course/{courseId}")
    public ResponseEntity<String> deleteCourseById(@PathVariable("courseId") Integer courseId) throws RecordNotFoundException {
        csService.deleteCourseById(courseId);
        return new ResponseEntity<String>("Course " + courseId + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("course/{courseId}/S{studentId}")
    public ResponseEntity<String> deleteCourseStudentById(@PathVariable("courseId") Integer courseId, @PathVariable("studentId") Integer studentId ) throws RecordNotFoundException {
        csService.deleteCourseStudentById(courseId, studentId);
        return new ResponseEntity<String>("Student " + studentId + " has been successfully deleted from course " + courseId, new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("course/{courseId}/T{teacherId}")
    public ResponseEntity<String> deleteCourseTeacherById(@PathVariable("courseId") Integer courseId, @PathVariable("teacherId") Integer teacherId ) throws RecordNotFoundException {
        csService.deleteCourseTeacherById(courseId, teacherId);
        return new ResponseEntity<String>("Teacher " + teacherId + " has been successfully deleted from course " + courseId, new HttpHeaders(), HttpStatus.OK);
    }
    
}