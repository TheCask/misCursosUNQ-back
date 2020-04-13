
package ar.edu.unq.misCursosUNQ.DAO;

import ar.edu.unq.misCursosUNQ.AbstractEntity;

public interface GenericDao {

	// Almacena un objeto en base de datos
	public void save(final AbstractEntity object);

	// Elimina un objecto de la base de datos
	public void delete(final AbstractEntity object);

}