package edu.syr.project.trelloclone.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.syr.project.trelloclone.data.models.History;
import edu.syr.project.trelloclone.data.models.Task;
import edu.syr.project.trelloclone.data.repository.HistoryRepository;
import edu.syr.project.trelloclone.data.repository.TaskRepository;
import edu.syr.project.trelloclone.data.repository.UserRepository;
import edu.syr.project.trelloclone.data.repository.UserTaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTaskRepository userTaskRepository;

    @Autowired
    private HistoryRepository historyRepository;

    // Will Retrieve all tasks from the database
    public List<Task> getAllTasks() {
        List<Task> tasksList = new ArrayList<>();
        taskRepository.findAll().forEach(tasksList::add);
        return tasksList;
    }

    // Will create a new task in the database
    public Task createTask(Task task) {
        Task newTask = taskRepository.save(new Task(task.getState(), task.getDescription()));

        // Will Create a history entry for the newly task being added
        History history = new History();
        history.setTask(newTask);
        history.setChange_id(newTask.getTaskId());
        history.setChange_id_type("Task id");
        history.setChange_description("Task Inserted");
        history.setUpdated_on(newTask.getModified_on());
        historyRepository.save(history);

        return newTask;
    }

    // Will Update the description of the task
    public Task updateTaskDescription(Task task, long id) throws Exception {
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found Task with id = " + id));

        // Will Create a history entry for the task description - updated
        History history = new History();
        history.setTask(taskToUpdate);
        history.setChange_id(taskToUpdate.getTaskId());
        history.setChange_id_type("Task id");
        taskToUpdate.setDescription(task.getDescription());
        history.setChange_description("Task description updated to: " + taskToUpdate.getDescription());
        taskToUpdate.setModified_on(LocalDateTime.now());
        history.setUpdated_on(taskToUpdate.getModified_on());
        historyRepository.save(history);
        taskRepository.save(taskToUpdate);

        return taskToUpdate;
    }

    // Will Update the state of the task
    public Task updateTaskState(Task task, Long id) throws Exception {
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found Task with id = " + id));

        // Will Create a history entry for the task state - updated
        History history = new History();
        history.setTask(taskToUpdate);
        history.setChange_id(taskToUpdate.getTaskId());
        history.setChange_id_type("Task id");
        history.setChange_description("Task state updated from: " + taskToUpdate.getState() + " to: " + task.getState());
        taskToUpdate.setState(task.getState());
        taskToUpdate.setModified_on(LocalDateTime.now());
        history.setUpdated_on(taskToUpdate.getModified_on());
        historyRepository.save(history);

        return taskToUpdate;
    }

    // Will Find a task with the ID
    public Task findById(long id) throws Exception {
        return taskRepository.findTaskByTaskId(id);
    }

    // Will Delete the task based on ID
    public void deleteTask(long id) throws Exception {
        taskRepository.deleteById(id);
    }
}
