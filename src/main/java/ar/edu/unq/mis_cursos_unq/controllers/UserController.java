package ar.edu.unq.mis_cursos_unq.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.mis_cursos_unq.User;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.UserException;
import ar.edu.unq.mis_cursos_unq.services.UserService;
 
@RestController
@RequestMapping("/api")
public class UserController {
    
	public static final int USER_PER_PAGE = 20;
	
	@Autowired
    private UserService usService;
 
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> list = usService.getUsers();
        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) throws RecordNotFoundException {
        User entity = usService.getUserById(id);
        return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/user")
    public @ResponseBody ResponseEntity<User> createOrUpdateUser(@RequestBody User user) throws RecordNotFoundException, UserException {
    	User updated = usService.createOrUpdateUser(user);
        return new ResponseEntity<User>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id) throws RecordNotFoundException, UserException {
        usService.deleteUserById(id);
        return new ResponseEntity<String>("User " + id + " has been successfully deleted", new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/users/search")
    public List<User> searchUsers(@RequestParam(value="text", required=false) String text,
    		@RequestParam(value="page", required=false) Integer page,
    		ModelMap model){

    	if (text == null && page == null) { return new ArrayList<User>(); }

    	if (text != null && page == null){
    		page = 1;
    		model.put("pageNo", 1);
    	}

    	model.addAttribute("resultsCount", usService.searchUsersResultsCount(text));

    	model.addAttribute("pageCount", usService.searchUsersPagesCount(text, USER_PER_PAGE));

    	model.addAttribute("userList", usService.searchUsers(text, page, USER_PER_PAGE));
    	
    	@SuppressWarnings("unchecked")
    	List<User> resultUserList = (List<User>) model.getAttribute("userList");
    	
    	return resultUserList;
	}
}