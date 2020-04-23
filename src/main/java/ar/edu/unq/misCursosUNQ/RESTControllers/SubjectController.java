//package ar.edu.unq.misCursosUNQ.RESTControllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import ar.edu.unq.misCursosUNQ.Subject;
//import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
//import ar.edu.unq.misCursosUNQ.Services.SubjectService;
// 
//@RestController
//@RequestMapping("/api")
//public class SubjectController {
//    
//	@Autowired
//    SubjectService service;
// 
//    @GetMapping("/subjects")
//    public ResponseEntity<List<Subject>> getAllSubjects() {
//        List<Subject> list = service.getSubjects();
//        return new ResponseEntity<List<Subject>>(list, new HttpHeaders(), HttpStatus.OK);
//    }
// 
//    @GetMapping("/subject/{code}")
//    public ResponseEntity<Subject> getSubjectByCode(@PathVariable("code") String code) throws RecordNotFoundException {
//        Subject entity = service.getSubjectByCode(code);
//        return new ResponseEntity<Subject>(entity, new HttpHeaders(), HttpStatus.OK);
//    }
// 
//    @PostMapping("/subject")
//    public ResponseEntity<Subject> createOrUpdateSubject(Subject subject) throws RecordNotFoundException {
//        Subject updated = service.createOrUpdateSubject(subject);
//        return new ResponseEntity<Subject>(updated, new HttpHeaders(), HttpStatus.OK);
//    }
// 
//    @DeleteMapping("/subject/{code}")
//    public HttpStatus deleteSubjectById(@PathVariable("code") String code) throws RecordNotFoundException {
//        service.deleteSubjectByCode(code);
//        return HttpStatus.FORBIDDEN;
//    }
// 
//}