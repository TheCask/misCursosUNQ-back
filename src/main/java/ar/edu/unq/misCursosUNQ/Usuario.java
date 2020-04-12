package ar.edu.unq.misCursosUNQ;

import java.util.List;

public class Usuario extends AbstractEntity {

	private String nombre;
	private String apellido;
	private String dni;
	private String email;
	
	//private List<Cursada> cursadas;
	private List<Materia> coordinadas;
	
	
	public Usuario(String nombre, String apellido, String dni, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
