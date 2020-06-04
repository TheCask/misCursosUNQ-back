package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.User;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Exceptions.UserException;
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
     
    public User getUserById(Integer id) throws RecordNotFoundException {
        Optional<User> user = usRepo.findById(id);
         
        if(user.isPresent()) { return user.get(); } 
        else { throw new RecordNotFoundException("User record not exist for given id"); }
    }
     
    @Transactional
    public User createOrUpdateUser(User entity) throws RecordNotFoundException {
    	
    	if (entity.getUserId() != null) {
    	
	    	Optional<User> user = usRepo.findById(entity.getUserId());
	
	    	if(user.isPresent()) {
	    		
	    		User newEntity = user.get();
	
	    		// TODO Update coordinatedSubjects, taughtCourses if needed
	    		newEntity.setPersonalData(entity.getPersonalData());
	    		newEntity.setJobDetail(entity.getJobDetail());
	
	    		return usRepo.save(newEntity);
	    	}
	    	throw new RecordNotFoundException("User record not exist for given id");
    	}
    	return usRepo.save(entity);
    } 
     
    @Transactional
    public void deleteUserById(Integer id) throws RecordNotFoundException, UserException {
        Optional<User> optUser = usRepo.findById(id);
        User user;
        if(optUser.isPresent()) {
        	user = optUser.get();
        	if (user.getCoordinatedSubjects().isEmpty() && 
        			user.getTaughtCourses().isEmpty()) { usRepo.deleteById(id); }
        	else { throw new UserException("User coordinates/teaches at least one subject/course and cannot be deleted");}
        	 
        } 
        else { throw new RecordNotFoundException("User record not exist for given id"); }
    }

	public List<User> getCoordinatorsByCode(String code) {
		List<User> coordinators = new ArrayList<User>();
		
		this.getUsers().stream().forEach(us -> { 
			if (us.coordinatesSubjectWithCode(code)) { 
				coordinators.add(us); 
			}
		});
		
		return coordinators;
	}
}