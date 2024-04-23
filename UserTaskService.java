package edu.syr.project.trelloclone.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.syr.project.trelloclone.data.models.History;
import edu.syr.project.trelloclone.data.models.Task;
import edu.syr.project.trelloclone.data.models.User;
import edu.syr.project.trelloclone.data.models.UserTask;
import edu.syr.project.trelloclone.data.repository.HistoryRepository;
import edu.syr.project.trelloclone.data.repository.TaskRepository;
import edu.syr.project.trelloclone.data.repository.UserRepository;
import edu.syr.project.trelloclone.data.repository.UserTaskRepository;

// Service which will be responsible for UserTask process based on and operations called from UserTaskController controller.
@Service
public class UserTaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTaskRepository userTaskRepository;

    @Autowired
    HistoryRepository historyRepository;

    // Will Assign the user to a task given
    public UserTask assignUserToTask(long userId, long taskId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Not found User with id = " + userId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Not found Task with id = " + taskId));

        // Will Create a instance of UserTask
        UserTask userTask = new UserTask();
        
        userTask.setTask(task);
        userTask.setUser(user);

        // Will Create a entry of history for the assignment of the user to a task
        History history = new History();
        
        history.setTask(task);
        
        history.setChange_id(user.getUserId());
        
        history.setChange_id_type("User id");
        
        history.setChange_description("User: " + user.getName() + " assigned to the task");
        
        history.setUpdated_on(task.getModified_on());
        
        historyRepository.save(history);
       
        
        

        return userTaskRepository.save(userTask);
    }

    // Will Delete a UserTask (in short, unassign the user from the task)
    public UserTask deleteUserTask(long userId, long taskId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Not found User with id = " + userId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Not found Task with id = " + taskId));

        // Will Create a entry of history  for un-assigning the user from a task
        
        History history = new History();
        
        history.setTask(task);
        history.setChange_id(user.getUserId());
        history.setChange_id_type("User id");
        history.setChange_description("User " + userId + " unassigned from the task");
        history.setUpdated_on(task.getModified_on());
        
        historyRepository.save(history);

        
        // Will Perform deletion operation of a UserTask by task in the database
        return userTaskRepository.deleteByTask(task);
    }
}
