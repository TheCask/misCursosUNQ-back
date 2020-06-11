package ar.edu.unq.mis_cursos_unq;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Calification implements Serializable {

	private static final long serialVersionUID = -4267537749379896955L;

	private Long calificationId;
	
	@JsonIgnoreProperties({"takenCourses", "attendedLessons", "careers"})
	private Student student;
	private Float note;
	
	// Default constructor for Hibernate
	public Calification() {}
	
	public Calification(Student student, Float note) {
		this.setStudent(student);
		this.setNote(note);
	}
		
	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCalificationId() { return calificationId; }
	
	/* Protected to avoid set the primary key */
	protected void setCalificationId(Long calificationId) { this.calificationId = calificationId; }
	
	public Student getStudent() { return student; }
	
	public void setStudent(Student student) { this.student = student; }
	
	public Float getNote() { return note; }
	
	public void setNote(Float note) { this.note = note; }
	
	

}
