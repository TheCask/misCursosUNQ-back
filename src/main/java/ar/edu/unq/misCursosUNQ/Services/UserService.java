package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.User;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.UserRepo;
 
@Service
public class UserService {
     
    @Autowired
    UserRepo usRepo;
     
    public List<User> getUsers() {
        List<User> userList = usRepo.findAll();
         
        if(userList.size() > 0) { return userList; } 
        else { return new ArrayList<User>(); }
    }
     
    public User getUserByDni(Integer dni) throws RecordNotFoundException {
        Optional<User> user = usRepo.findByDni(dni);
         
        if(user.isPresent()) { return user.get(); } 
        else { throw new RecordNotFoundException("User record not exist for given DNI"); }
    }
     
    @Transactional
    public User createOrUpdateUser(User entity) throws RecordNotFoundException {
    	
    	if (entity.getDni() != null) {
    	
	    	Optional<User> user = usRepo.findByDni(entity.getDni());
	
	    	if(user.isPresent()) {
	    		
	    		User newEntity = user.get();
	
	    		// TODO Check what happens with coordinatedSubjects, taughtCourses
				// this lists updates automagically when saving user entity by cascade anotations?
				// or has to be manually updated here in this service (more probably)?
	    		newEntity.setPersonalData(entity.getPersonalData());
	    		newEntity.setJobDetail(entity.getJobDetail());
	
	    		return usRepo.save(newEntity);
	    	}
	    	throw new RecordNotFoundException("User record not exist for given DNI");
    	}
    	return usRepo.save(entity);
    } 
     
    @Transactional
    public void deleteUserByDni(Integer dni) throws RecordNotFoundException {
        Optional<User> user = usRepo.findByDni(dni);
         
        if(user.isPresent()) { usRepo.deleteByDni(dni); } 
        else { throw new RecordNotFoundException("User record not exist for given DNI"); }
    } 
}