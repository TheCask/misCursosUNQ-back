package ar.edu.unq.mis_cursos_unq;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class JobDetail implements Serializable {

	private static final long serialVersionUID = -8986445721969296677L;
	
	private Integer jobDetailId;
	
	@Pattern(regexp = "^$|\\d{2}-\\d{8}-\\d")
	private String cuitNumber;
	
	@Size(max = 15)
	private String category;
	
	@Size(max = 2)
	private String grade; 
	
	@Size(max = 20)
	private String dedication; 
	
	@Size(max = 20)
	private String contractRelation; 
	
	@Min(0) @Max(50)
	private Integer aditionalHours;
	
	private String cvURL;
	
	private LocalDate lastUpdate;
	
	@Size(max = 200)
	private String gradeTitles;
	
	@Size(max = 200)
	private String posGradeTitles;
	
	public JobDetail() {
		this.setCuitNumber("");
		this.setCategory("");
		this.setGrade("");
		this.setDedication("");
		this.setContractRelation("");
		this.setAditionalHours(0);
		this.setCvURL("");
		this.setLastUpdate(LocalDate.now());
		this.setGradeTitles("");
		this.setPosGradeTitles("");
	}
	
	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getJobDetailId() { return jobDetailId; }

	/* Protected to avoid set the primary key */
	protected void setJobDetailId(Integer id) { this.jobDetailId = id; }
	
	public String getCuitNumber() { return cuitNumber; }
	
	public void setCuitNumber(String cuitNumber) { this.cuitNumber = cuitNumber; }
	
	public String getCategory() { return category; }
	
	public void setCategory(String category) { this.category = category; }
	
	public String getGrade() { return grade; }
	
	public void setGrade(String grade) { this.grade = grade.toUpperCase(); }
	
	public String getDedication() { return dedication; }
	
	public void setDedication(String dedication) { this.dedication = dedication; }
	
	public String getContractRelation() { return contractRelation; }
	
	public void setContractRelation(String contractRelation) { this.contractRelation = contractRelation; }
	
	public Integer getAditionalHours() { return aditionalHours; }
	
	public void setAditionalHours(Integer aditionalHours) { this.aditionalHours = aditionalHours; }
	
	public String getCvURL() { return cvURL; }
	
	public void setCvURL(String cvURL) { this.cvURL = cvURL; }
	
	public LocalDate getLastUpdate() { return lastUpdate; }
	
	public void setLastUpdate(LocalDate lastUpdate) { this.lastUpdate = lastUpdate; }
	
	public String getGradeTitles() { return gradeTitles; }
	
	public void setGradeTitles(String gradeTitles) { this.gradeTitles = gradeTitles; }
	
	public String getPosGradeTitles() { return posGradeTitles; }
	
	public void setPosGradeTitles(String posGradeTitles) { this.posGradeTitles = posGradeTitles; }

	/* METHODS */
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobDetail)) return false;
        return jobDetailId != null && jobDetailId.equals(((JobDetail) o).getJobDetailId());
    }
}