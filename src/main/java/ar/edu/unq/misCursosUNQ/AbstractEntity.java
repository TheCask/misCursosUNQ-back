package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
 
	private static final long serialVersionUID = -4744446709567257390L;
	
	protected Long id;
 
	// Abstract method because anotations are not heredable 
	public abstract Long getId();
 
	/* 
	 * This method is protected to avoid using identifiers in the instance,
	 * because they has to be managed by the persistence layer
	*/
	protected void setId(final Long id) {
		this.id = id;
	}
}