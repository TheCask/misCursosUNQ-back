package ar.edu.unq.mis_cursos_unq.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.mis_cursos_unq.Course;
import ar.edu.unq.mis_cursos_unq.User;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.UserException;
import ar.edu.unq.mis_cursos_unq.repos.UserDAO;
import ar.edu.unq.mis_cursos_unq.repos.UserRepo;
 
@Service
public class UserService {
     
    @Autowired
    private UserRepo usRepo;
    
    @Autowired
    private UserDAO usDAO;
     
    public List<User> getUsers() {
        List<User> userList = usRepo.findAll();
         
        if(userList.size() > 0) { return userList; } 
        else { return new ArrayList<User>(); }
    }
    
    public List<User> getUsersByActiveState(Boolean isActive) {
		List<User> userList = new ArrayList<User>();
		this.getUsers().stream().forEach(us -> {
        	if (isActive && us.getIsActive()) { userList.add(us); }
        	else if (!isActive && !us.getIsActive()) { userList.add(us); }
        });
		return userList;
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
	    		newEntity.setIsActive(entity.getIsActive());
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
        if(optUser.isPresent()) {
        	User user = optUser.get();
        	if (user.canBeInactivated()) { user.setIsActive(false); }
        	else { throw new UserException("User coordinates/teaches at least one active subject/course and cannot be deleted");}
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
	
	public List<Course> getUserCoursesByEmailAndOpenState(String email, Boolean isOpen) throws RecordNotFoundException {
		
		List<User> userList = this.getUsersByEmail(email);
		
		List<Course> courseList = userList.get(0).taughtCoursesByOpenState(isOpen);
		
		return courseList;
	}

	public List<Course> getUserCoursesByEmail(String email) throws RecordNotFoundException {
		
		List<User> userList = this.getUsersByEmail(email);
		
		List<Course> courseList = userList.get(0).getTaughtCourses();
		
		return courseList;
	}
	
	public List<User> getUsersByEmail(String email) throws RecordNotFoundException {
		List<User> userList = new ArrayList<User>();
		
		this.getUsers().stream().forEach(us -> { 
			if (us.getPersonalData().getEmail().equals(email)) { userList.add(us); }
		});
		
		if(!userList.isEmpty()) { return userList; }
		else { throw new RecordNotFoundException("User record not exist for given email"); }
	}
	
	@Transactional
	public Integer searchUsersResultsCount(String searchText) {
		return usDAO.searchUsersTotalCount(searchText);
	}

	public Integer searchUsersPagesCount(String searchText, int userPerPage) {
		Integer userCount = this.searchUsersResultsCount(searchText);
		
		return (Integer) Math.floorDiv(userCount, userPerPage) + 1;
	}
	
	@Transactional
	public List<User> searchUsers(String searchText, Integer pageNo, int userPerPage) {
		return usDAO.searchUsers(searchText, pageNo, userPerPage);
	}
}