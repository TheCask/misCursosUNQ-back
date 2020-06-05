package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Evaluation implements Serializable{

	private static final long serialVersionUID = 5032810372266105358L;
	
	private Long evaluationId;
	private String instanceName;
	private Map<Student, Float> attendantStudentCalificationMap;
	
	public Evaluation() {}
	
	public Evaluation(String instanceName) {
		this.setInstanceName(instanceName);
		this.setAttendantStudentCalificationMap(new HashMap<Student, Float>());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getEvaluationId() { return evaluationId; }

	/* Protected to avoid set the primary key */
	protected void setEvaluationId(Long evaluationId) { this.evaluationId = evaluationId; }

	public String getInstanceName() { return instanceName; }

	public void setInstanceName(String instanceName) { this.instanceName = instanceName; }

	//@Column
    @ElementCollection
	public Map<Student, Float> getAttendantStudentCalificationMap() { return attendantStudentCalificationMap; }

    // Not allowed to set califications map directly because database corruption
    private void setAttendantStudentCalificationMap(Map<Student, Float> attendantStudentCalificationMap) { 
		this.attendantStudentCalificationMap = attendantStudentCalificationMap; 
	}
    
    /* METHODS */
    
    public void setStudentCalification(Student student, Float calification) {
		attendantStudentCalificationMap.put(student, calification);
		
	}
    
    public void replaceStudentCalification(Student student, Float calification) {
		attendantStudentCalificationMap.replace(student, calification);
		
	}
    
    public void deleteStudentCalification(Student student) {
		attendantStudentCalificationMap.remove(student);
		
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evaluation)) return false;
        return evaluationId != null && evaluationId.equals(((Evaluation) o).getEvaluationId());
    }	
}