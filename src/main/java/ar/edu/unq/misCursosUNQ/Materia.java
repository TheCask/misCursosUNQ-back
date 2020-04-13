package ar.edu.unq.misCursosUNQ;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// Remember to include only JPA API annotations (javax.persistence.*) 
// to decouple hibernate from application code.

@Entity
@Table(name="MATERIA")
public class Materia extends AbstractEntity {
	
	private static final long serialVersionUID = -3642091487086232955L;

	private String nombre;

	@ManyToMany(mappedBy = "coordinadas")
	public List<Usuario> coordinadores;
	
	//@ManyToMany(mappedBy = "?????")
	//public List<Usuario> docentes;
	
	public Materia(String nombre) {
		this.nombre = nombre;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() { return id; }
	
	// To print materia basic details in logs.
	@Override
    public String toString() {
        return "MateriaEntity [id=" + this.getId() + ", nombre=" + nombre + "]";
    }

	public String getNombre() { return this.nombre; }

	public void setNombre(String nombre) { this.nombre = nombre; }

}
