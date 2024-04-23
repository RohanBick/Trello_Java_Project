package edu.syr.project.trelloclone.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.syr.project.trelloclone.data.models.User;
import edu.syr.project.trelloclone.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // Will Retrieve all the users or users by the given name
    public List<User> getAllUsers(String name) {
        List<User> users = new ArrayList<>();

        // Will Check if a name is specified or present in the database, if so, will find the users by the name; otherwise, retrieves all the users
        if (name == null)
            userRepository.findAll().forEach(users::add);
        else
            userRepository.findByName(name).forEach(users::add);
        return users;
    }

    // Will fetch/ Get the user by ID details
    public User getUserById(long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found user with id = " + id));
    }

    // Will Update the user details by ID
    public User updateUser(long id, User user) throws Exception {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found user with id = " + id));

        // Will Update the user's name
        userToUpdate.setName(user.getName());

        // Will Save the user details - updated
        return userRepository.save(userToUpdate);
    }

    // Will Delete the user by the ID
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
