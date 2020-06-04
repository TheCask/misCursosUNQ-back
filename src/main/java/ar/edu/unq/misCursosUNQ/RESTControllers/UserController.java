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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.misCursosUNQ.User;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Exceptions.UserException;
import ar.edu.unq.misCursosUNQ.Services.UserService;
 
@RestController
@RequestMapping("/api")
public class UserController {
    
	@Autowired
    UserService usService;
 
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
}