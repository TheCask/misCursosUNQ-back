package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JobDetail implements Serializable {

	private static final long serialVersionUID = -8986445721969296677L;
	
	private Integer id;
	private Long cuitNumber;
	private String category;
	private String grade; 
	private String dedication; 
	private String contractRelation; 
	private Integer aditionalHours;
	private String cvURL; 
	private LocalDate lastUpdate;
	private List<String> gradeTitles;
	private List<String> posGradeTitles;
	
	// Default constructor for Hibernate
	//public JobDetail() {}
	
	public JobDetail() {
		this.setCuitNumber(0L);
		this.setCategory("");
		this.setGrade("");
		this.setDedication("");
		this.setContractRelation("");
		this.setAditionalHours(0);
		this.setCvURL("");
		this.setLastUpdate(LocalDate.ofEpochDay(0));
		this.setGradeTitles(new ArrayList<String>());
		this.setPosGradeTitles(new ArrayList<String>());
	}
	
	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() { return id; }

	/* Protected to avoid set the primary key */
	protected void setId(Integer id) { this.id = id; }
	
	public Long getCuitNumber() { return cuitNumber; }
	
	public void setCuitNumber(Long cuitNumber) { this.cuitNumber = cuitNumber; }
	
	public String getCategory() { return category; }
	
	public void setCategory(String category) { this.category = category; }
	
	public String getGrade() { return grade; }
	
	public void setGrade(String grade) { this.grade = grade; }
	
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
	
	@Column
    @ElementCollection
	public List<String> getGradeTitles() { return gradeTitles; }
	
	public void setGradeTitles(List<String> gradeTitles) { this.gradeTitles = gradeTitles; }
	
	@Column
    @ElementCollection
	public List<String> getPosGradeTitles() { return posGradeTitles; }
	
	public void setPosGradeTitles(List<String> posGradeTitles) { this.posGradeTitles = posGradeTitles; }
	
}
