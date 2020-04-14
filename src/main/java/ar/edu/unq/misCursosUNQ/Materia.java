package ar.edu.unq.misCursosUNQ;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

// Remember to include only JPA API annotations (javax.persistence.*) 
// to decouple hibernate from application code.

@Entity
//@Table(name="MATERIA")
public class Materia extends AbstractEntity {
	
	private static final long serialVersionUID = -3642091487086232955L;

	//@Column(name="nombre")
	private String nombre;

	@ManyToMany(mappedBy = "coordinadas")
	public List<Usuario> coordinadores;
	
	//@ManyToMany(mappedBy = "?????")
	//public List<Usuario> docentes;
	
	public Materia() {}
	
	public Materia(String nombre) { this.nombre = nombre; }
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() { return id; }

	public String getNombre() { return this.nombre; }

	public void setNombre(String nombre) { this.nombre = nombre; }
	
	// To print materia basic details in logs.
	@Override
	public String toString() {
		return "MateriaEntity [id=" + this.getId() + ", nombre=" + nombre + "]";
	}

}
