package ar.edu.unq.misCursosUNQ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
//@Table(name="USUARIO")
public class Usuario extends AbstractEntity {

	private static final long serialVersionUID = 6671561417676772045L;

	private String nombre;
	private String apellido;
	private String dni;
	private String email;
	
	@ManyToMany
	@JoinTable(name="COORDINADOR_MATERIA")
	private List<Materia> coordinadas;
	
	//private List<Cursada> cursadas;
	
	public Usuario(String nombre, String apellido, String dni, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
	}

	@Override
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	public Long getId() { return id; }
}
