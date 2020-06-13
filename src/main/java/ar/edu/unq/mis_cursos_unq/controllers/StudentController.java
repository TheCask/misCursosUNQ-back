package ar.edu.unq.mis_cursos_unq.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import ar.edu.unq.mis_cursos_unq.Student;
import ar.edu.unq.mis_cursos_unq.exceptions.PersonalDataException;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.services.StudentService;
 
@RestController
@RequestMapping("/api")
public class StudentController {
	
	public static final int STUDENT_PER_PAGE = 20;
    
	@Autowired
    private StudentService stService;
 
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> list = stService.getStudents();
        return new ResponseEntity<List<Student>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("student/{fileNumber}")
    public ResponseEntity<Student> getStudentByFileNumber(@PathVariable("fileNumber") Integer fileNumber) throws RecordNotFoundException {
        Student entity = stService.getStudentByFileNumber(fileNumber);
        return new ResponseEntity<Student>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/student")
    public @ResponseBody ResponseEntity<Student> createOrUpdateStudent(@RequestBody Student student) throws RecordNotFoundException, PersonalDataException {
    	Student updated = stService.createOrUpdateStudent(student);
        return new ResponseEntity<Student>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("student/{fileNumber}")
    public ResponseEntity<String> deleteStudentByFileNumber(@PathVariable("fileNumber") Integer fileNumber) throws RecordNotFoundException {
        stService.deleteStudentByFileNumber(fileNumber);
        return new ResponseEntity<String>("Student " + fileNumber + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);
    }
    
    
	@GetMapping("/students/search")
    public List<Student> searchStudents(@RequestParam(value="text", required=false) String text,
    		@RequestParam(value="page", required=false) Integer page,
    		ModelMap model){

    	if (text == null && page == null) { return new ArrayList<Student>(); }

    	if (text != null && page == null){
    		page = 1;
    		model.put("pageNo", 1);
    	}

    	model.addAttribute("resultsCount", stService.searchStudentsResultsCount(text));

    	model.addAttribute("pageCount", stService.searchStudentsPagesCount(text, STUDENT_PER_PAGE));

    	model.addAttribute("studentList", stService.searchStudents(text, page, STUDENT_PER_PAGE));
    	
    	@SuppressWarnings("unchecked")
    	List<Student> resultStudentList = (List<Student>) model.getAttribute("studentList");
    	
    	return resultStudentList;
	}
    
}