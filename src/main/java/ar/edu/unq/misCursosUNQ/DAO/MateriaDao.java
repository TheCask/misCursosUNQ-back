package ar.edu.unq.misCursosUNQ.DAO;
 
import java.util.List;

import ar.edu.unq.misCursosUNQ.Materia;
 
public interface MateriaDao extends GenericDao {
 
	// Recupera todas las materias
	public List<Materia> findAll();
 
	// Recupera una materia buscandola por su id 
	public Materia findById(final Long id);
}