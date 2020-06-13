package ar.edu.unq.mis_cursos_unq.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unq.mis_cursos_unq.Evaluation;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.services.EvaluationService;
 
@RestController
@RequestMapping("/api")
public class EvaluationController {
    
	@Autowired
    private EvaluationService evService;
 
    @GetMapping("/evaluations")
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> list = evService.getEvaluations();
        return new ResponseEntity<List<Evaluation>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/evaluation/{evaluationId}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable("evaluationId") Long evaluationId) throws RecordNotFoundException {
        Evaluation entity = evService.getEvaluationById(evaluationId);
        return new ResponseEntity<Evaluation>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/evaluation")
    public @ResponseBody ResponseEntity<Evaluation> createOrUpdateEvaluation(@RequestBody Evaluation evaluation) throws RecordNotFoundException {
    	Evaluation updated = evService.createOrUpdateEvaluation(evaluation);
        return new ResponseEntity<Evaluation>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("/evaluation/{evaluationId}")
    public ResponseEntity<String> deleteEvaluationById(@PathVariable("evaluationId") Long evaluationId) throws RecordNotFoundException, SubjectException, SeasonException {
        evService.deleteEvaluationById(evaluationId);
        return new ResponseEntity<String>("Evaluation " + evaluationId + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);   
    }
}