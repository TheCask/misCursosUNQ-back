package ar.edu.unq.misCursosUNQ.DAO;
 
import java.util.List;

import ar.edu.unq.misCursosUNQ.Usuario;
 
public interface UsuarioDao extends GenericDao {
 
	// Recupera todos los usuarios de todas las materias
	public List<Usuario> findAll();
 
	// Recupera un usuario buscandolo por su id 
	public Usuario findById(final Long id);
}