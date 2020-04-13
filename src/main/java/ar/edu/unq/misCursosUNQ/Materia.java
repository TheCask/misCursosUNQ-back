package ar.edu.unq.misCursosUNQ;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="COMPANY")
public class Materia extends AbstractEntity {
	
	private String nombre;

	@ManyToMany(mappedBy = "coordinadas")
	public List<Usuario> coordinadores;
	
	//@ManyToMany(mappedBy = "?????")
	//public List<Usuario> docentes;
	
	public Materia(String nombre) {
		this.nombre = nombre;
	}
	
	

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
