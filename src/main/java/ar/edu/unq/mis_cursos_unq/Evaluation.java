package ar.edu.unq.mis_cursos_unq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Evaluation implements Serializable{

	private static final long serialVersionUID = 5032810372266105358L;
	
	private Long evaluationId;
	private String instanceName;
	
	@JsonIgnoreProperties({"takenCourses", "attendedLessons", "careers", "personalData"})
	private List<Calification> califications;
	
	public Evaluation() {}
	
	public Evaluation(String instanceName) {
		this.setInstanceName(instanceName);
		this.setCalifications(new ArrayList<Calification>());
	}

	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getEvaluationId() { return evaluationId; }

	/* Protected to avoid set the primary key */
	protected void setEvaluationId(Long evaluationId) { this.evaluationId = evaluationId; }

	public String getInstanceName() { return instanceName; }

	public void setInstanceName(String instanceName) { this.instanceName = instanceName; }

	@OneToMany(cascade = { CascadeType.ALL })
	public List<Calification> getCalifications() { return califications; }

    // Not allowed to set califications map directly because database corruption
    public void setCalifications(List<Calification> califications) { 
		this.califications = califications; 
	}
    
    /* METHODS */
    
    public void setStudentNote(Student student, Float note) {
		Calification calification = new Calification(student, note);
    	this.getCalifications().add(calification);
  	
	}
    
    public void replaceStudentCalification(Student student, Float note) {
		if (this.deleteStudentCalification(student)) {
			this.setStudentNote(student, note);
		}
	}
    
    public Boolean deleteStudentCalification(Student student) {
    	return this.getCalifications().removeIf(cl -> cl.getStudent().equals(student));
	}
    
    // TODO Test
    public void removeAllCalifications() {
		this.getCalifications().clear();
	}
    
    // TODO Test
    public Boolean isStudentEvaluated(Student st) {
    	return this.getCalifications().stream().anyMatch(cl -> cl.getStudent().equals(st));
	}
    
    // TODO Test
    public Optional<Calification> getStudentCalification(Student st) {
    	return this.getCalifications().stream().filter(cl -> cl.getStudent().equals(st)).findFirst();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evaluation)) return false;
        return evaluationId != null && evaluationId.equals(((Evaluation) o).getEvaluationId());
    }
}