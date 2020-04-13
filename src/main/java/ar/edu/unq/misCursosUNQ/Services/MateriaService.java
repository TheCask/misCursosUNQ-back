package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ar.edu.unq.misCursosUNQ.Materia;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.MateriaRepo;
 
@Service
public class MateriaService {
     
    @Autowired
    MateriaRepo repository;
     
    public List<Materia> getMaterias() {
        List<Materia> materiaList = repository.findAll();
         
        if(materiaList.size() > 0) { return materiaList; } 
        else { return new ArrayList<Materia>(); }
    }
     
    public Materia getMateriaById(Long id) throws RecordNotFoundException {
        Optional<Materia> materia = repository.findById(id);
         
        if(materia.isPresent()) { return materia.get(); } 
        else { throw new RecordNotFoundException("Materia record not exist for given id"); }
    }
     
    public Materia createOrUpdateMateria(Materia entity) throws RecordNotFoundException {
        Optional<Materia> materia = repository.findById(entity.getId());
         
        if(materia.isPresent()) {
            Materia newEntity = materia.get();
            
            newEntity.setNombre(entity.getNombre());
 
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } 
        else {
            entity = repository.save(entity); 
            return entity;
        }
    } 
     
    public void deleteMateriaById(Long id) throws RecordNotFoundException {
        Optional<Materia> materia = repository.findById(id);
         
        if(materia.isPresent()) { repository.deleteById(id); } 
        else { throw new RecordNotFoundException("Materia record not exist for given id"); }
    } 
}