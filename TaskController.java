package edu.syr.project.trelloclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.syr.project.trelloclone.data.models.History;
import edu.syr.project.trelloclone.data.models.Task;
import edu.syr.project.trelloclone.data.repository.HistoryRepository;
import edu.syr.project.trelloclone.data.repository.TaskRepository;
import edu.syr.project.trelloclone.data.repository.UserRepository;
import edu.syr.project.trelloclone.data.repository.UserTaskRepository;
import edu.syr.project.trelloclone.data.service.TaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//TaskController for executing task based api calls
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTaskRepository userTaskRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    TaskService taskService;


    //Get all tasks in the task table
    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTask() {

        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    //Get the task details with the specified taskId in the task table
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) throws Exception {

        Task task = taskService.findById(id);

        if(task!=null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);

    }

    //Insert task with the specified task details in the task table
    @PostMapping("/task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        Task _task= taskService.createTask(task);
        if(_task!=null) {
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(task, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Update task with the given task details for the specified taskId in the task table
    @PutMapping("/task/updateDescription/{id}")
    public ResponseEntity<Task> updateTaskDescription(@PathVariable("id") long id, @RequestBody Task task) throws Exception {

        Task _task = taskService.updateTaskDescription(task, id);
        if(_task!=null) {
            return new ResponseEntity<>(_task, HttpStatus.OK);
        }
        return new ResponseEntity<>(task, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/task/updateState/{id}")
    public ResponseEntity<Task> updateTaskState(@PathVariable("id") long id, @RequestBody Task task) throws Exception {
        Task _task = taskService.updateTaskState(task,id);

        return new ResponseEntity<>(taskRepository.save(_task), HttpStatus.OK);
    }

    //Delete task with the specified taskId in the task table
    @DeleteMapping("/task/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) throws Exception {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
