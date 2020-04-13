package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
 
	protected Long id;
 
	// Este metodo es abstracto porque las anotaciones no son heredables 
	public abstract Long getId();
 
	// Este metodo es protegido para evitar que un programador pueda poner un
	// idenfificador en la instancia, ya que los identitificadores deben ser
	// gestionados por la capa de persistencia
	protected void setId(final Long id) {
		this.id = id;
	}
}