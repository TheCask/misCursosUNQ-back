package ar.edu.unq.misCursosUNQ.RESTControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.misCursosUNQ.Materia;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Services.MateriaService;
 
@RestController
@RequestMapping("/materias")
public class MateriaController {
    
	@Autowired
    MateriaService service;
 
    @GetMapping
    public ResponseEntity<List<Materia>> getAllEmployees() {
        List<Materia> list = service.getMaterias();
        return new ResponseEntity<List<Materia>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Materia> getMateriaById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Materia entity = service.getMateriaById(id);
        return new ResponseEntity<Materia>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Materia> createOrUpdateMateria(Materia materia) throws RecordNotFoundException {
        Materia updated = service.createOrUpdateMateria(materia);
        return new ResponseEntity<Materia>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteMateriaById(@PathVariable("id") Long id) throws RecordNotFoundException {
        service.deleteMateriaById(id);
        return HttpStatus.FORBIDDEN;
    }
 
}