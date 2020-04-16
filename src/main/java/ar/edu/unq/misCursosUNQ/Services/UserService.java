package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ar.edu.unq.misCursosUNQ.User;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.UserRepo;
 
@Service
public class UserService {
     
    @Autowired
    UserRepo repository;
     
    public List<User> getUsers() {
        List<User> userList = repository.findAll();
         
        if(userList.size() > 0) { return userList; } 
        else { return new ArrayList<User>(); }
    }
     
    public User getUserById(Long id) throws RecordNotFoundException {
        Optional<User> user = repository.findById(id);
         
        if(user.isPresent()) { return user.get(); } 
        else { throw new RecordNotFoundException("User record not exist for given id"); }
    }
     
    public User createOrUpdateUser(User entity) throws RecordNotFoundException {
        
    	if (entity.getDni() == null) { return repository.save(entity); }
    	else {
    	
	    	Optional<User> user = repository.findBydni(entity.getDni());
	         
	        if(user.isPresent()) {
	            User newEntity = user.get();
	            
	            newEntity.setFirstName(entity.getFirstName());
	            newEntity.setLastName(entity.getLastName());
	            newEntity.setEmail(entity.getEmail());
	            newEntity.setCoordinatedSubjects(entity.getCoordinatedSubjects());
	 
	            newEntity = repository.save(newEntity);
	             
	            return newEntity;
	        } 
	        else { return repository.save(entity); }
    	}
    } 
     
    public void deleteUserById(Long id) throws RecordNotFoundException {
        Optional<User> user = repository.findById(id);
         
        if(user.isPresent()) { repository.deleteById(id); } 
        else { throw new RecordNotFoundException("User record not exist for given id"); }
    } 
}