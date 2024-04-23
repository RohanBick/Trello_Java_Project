package edu.syr.project.trelloclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.syr.project.trelloclone.data.models.User;
import edu.syr.project.trelloclone.data.repository.UserRepository;
import edu.syr.project.trelloclone.data.service.UserService;
import java.util.List;

//UserController for executing user based api calls
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    //Get all users from the user table
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {

        List<User> users = userService.getAllUsers(name);
        if(users!=null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(users,HttpStatus.NOT_FOUND);
    }

    //Get user details with the specified userId from user table
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) throws Exception {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Insert user with the specified user details in the user table
    @PostMapping("/user")
    public ResponseEntity<User> createTutorial(@RequestBody User user) {
        User _user = userRepository.save(new User(user.getName()));
        if(_user!=null) {
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(_user, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Update user details for the specified userId  in the user table
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) throws Exception {
        User _user = userService.updateUser(id,user);
        if(_user!=null) {
            return new ResponseEntity<>(_user, HttpStatus.OK);
        }
        return new ResponseEntity<>(_user, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Delete user with the specified userId from the user table
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
