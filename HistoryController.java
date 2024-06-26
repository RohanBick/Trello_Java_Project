package edu.syr.project.trelloclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.syr.project.trelloclone.data.models.*;
import edu.syr.project.trelloclone.data.repository.HistoryRepository;
import edu.syr.project.trelloclone.data.repository.TaskRepository;
import edu.syr.project.trelloclone.data.repository.UserTaskRepository;
import edu.syr.project.trelloclone.data.service.HistoryService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class HistoryController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    HistoryService historyService;

    @GetMapping("/history/{taskId}")
    public ResponseEntity<List<History>> getHistoryOfTask(@PathVariable(value = "taskId") Long taskId) throws Exception {

        List<History> history = historyService.getHistoryOfTask(taskId);
        if(history!=null) {
            return new ResponseEntity<>(history, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
