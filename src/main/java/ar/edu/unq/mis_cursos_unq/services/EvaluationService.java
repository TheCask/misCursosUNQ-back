package ar.edu.unq.mis_cursos_unq.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.mis_cursos_unq.Calification;
import ar.edu.unq.mis_cursos_unq.Course;
import ar.edu.unq.mis_cursos_unq.Evaluation;
import ar.edu.unq.mis_cursos_unq.Student;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.repos.CalificationRepo;
import ar.edu.unq.mis_cursos_unq.repos.EvaluationRepo;

@Service
public class EvaluationService {

	@Autowired
	EvaluationRepo evRepo;
	
	@Autowired
	CalificationRepo clRepo;
	
	@Autowired
	CourseService csService;
	
	@Autowired
	StudentService stService;
	
	public List<Evaluation> getEvaluations() {
		List<Evaluation> evList = evRepo.findAll();
        
        if(evList.size() > 0) { return evList; } 
        else { return new ArrayList<Evaluation>(); }
	}

	public Evaluation getEvaluationById(Long evaluationId) throws RecordNotFoundException {
		Optional<Evaluation> optional = evRepo.findById(evaluationId);
        
        if(optional.isPresent()) { return optional.get(); } 
        else { throw new RecordNotFoundException("Evaluation record does not exist for given id"); }
	}
	

	public List<Evaluation> getStudentEvaluations(Student st) {
		List<Evaluation> evList = new ArrayList<Evaluation>();
			
		this.getEvaluations().stream().forEach(ev -> {
			if (ev.isStudentEvaluated(st)) { evList.add(ev); }
		});
		return evList;
	}

	@Transactional
	public Evaluation createOrUpdateEvaluation(Evaluation entity) throws RecordNotFoundException {
		// Update Evaluation
    	if (entity.getEvaluationId() != null) {
    		
	    	Optional<Evaluation> optEntity = evRepo.findById(entity.getEvaluationId());
	
	    	if(optEntity.isPresent()) { // Evaluation exists in db
	    		
	    		Evaluation newEntity = optEntity.get();
	    		
	    		newEntity.setInstanceName(entity.getInstanceName());
	    		
	    		this.updateCalifications(newEntity, entity);
	    			
	    		return evRepo.save(newEntity);
	    	}
	    	throw new RecordNotFoundException("Evaluation record does not exist for given id");
    	}
    	// New Evaluation
	    return evRepo.save(entity);
	}
	
	protected void updateCalifications(Evaluation dbEvaluation, Evaluation newDataEvaluation) {
    	dbEvaluation.removeAllCalifications();
		newDataEvaluation.getCalifications().forEach(cl -> {
			try { 
				Student student = stService.getStudentByFileNumber(cl.getStudent().getFileNumber());
				dbEvaluation.setStudentNote(student, cl.getNote());
			} 
			catch (RecordNotFoundException e) { e.printStackTrace(); }
		});
	}

	@Transactional
	public void deleteEvaluationById(Long evaluationId) throws RecordNotFoundException, SeasonException, SubjectException {
		
		Optional<Evaluation> optEntity = evRepo.findById(evaluationId);
        
        if(optEntity.isPresent()) { 
        	
        	Evaluation evaluation = optEntity.get();
        	
        	Optional<Course> optEvCourse = csService.getCourseByEvaluationId(evaluation.getEvaluationId());
        	
        	if (optEvCourse.isPresent()) {
        		optEvCourse.get().removeEvaluation(evaluation);
        		evRepo.deleteById(evaluation.getEvaluationId());
        		csService.createOrUpdateCourse(optEvCourse.get());
        	}
        } 
        else { throw new RecordNotFoundException("Evaluation record does not exist for given id"); }
		
	}

	@Transactional
	protected void removeCourseStudentCalifications(Course course, Student st) {
		
		List<Evaluation> evList = new ArrayList<Evaluation>();
		
		course.getEvaluations().stream().forEach(ev -> {
			if (ev.isStudentEvaluated(st)) { evList.add(ev); }
		});
		
		evList.forEach(ev -> {
			Optional<Evaluation> optEv = evRepo.findById(ev.getEvaluationId());
			if (optEv.isPresent()) {
				Optional<Calification> optCl = clRepo.findById(optEv.get().getEvaluationId());
				if (optCl.isPresent()) {
					optEv.get().deleteStudentCalification(st);
					clRepo.delete(optCl.get());
				}
			}
			evRepo.save(ev);
		});
	}
}