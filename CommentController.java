package edu.syr.project.trelloclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.syr.project.trelloclone.data.models.*;
import edu.syr.project.trelloclone.data.repository.CommentRepository;
import edu.syr.project.trelloclone.data.repository.HistoryRepository;
import edu.syr.project.trelloclone.data.repository.TaskRepository;
import edu.syr.project.trelloclone.data.repository.UserRepository;
import edu.syr.project.trelloclone.data.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

//TaskController for executing comment based api calls
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private CommentService commentService;

    //Get all task comments for the given taskId from the comment table
    @GetMapping("/task/{taskId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTaskId(@PathVariable(value = "taskId") Long taskId) throws Exception {

        List<Comment> comments = commentService.getAllCommentsByTaskId(taskId);

        if(comments!=null) {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(comments, HttpStatus.NOT_FOUND);
    }

    //Get the comment with the given commentId from the comment table
    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentsByCommentId(@PathVariable(value = "id") Long id) throws Exception {

        Comment comment = commentService.getCommentsByCommentId(id);
        if(comment!=null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        return new ResponseEntity<>(comment, HttpStatus.NOT_FOUND);
    }

    //Get all user comments for the given userId from the comment table
    @GetMapping("/user/{userId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByUserId(@PathVariable(value = "userId") Long userId) throws Exception {

        List<Comment> comments = commentService.getAllCommentsByUserId(userId);
        if(comments!=null) {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(comments, HttpStatus.NOT_FOUND);
    }

    //Insert comment for the given taskId in the comment table
    @PostMapping("/task/{taskId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "taskId") Long taskId,
                                                 @RequestBody CommentSchema commentRequest) throws Exception {

        Comment comment = commentService.createComment(taskId,commentRequest);
        if(comment!=null) {
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(comment, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Delete the comment with the given commentId from the comments table
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) throws Exception {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete comments for the task with the given taskId from the comments table
    @DeleteMapping("/task/{taskId}/comments")
    public ResponseEntity<List<Comment>> deleteAllCommentsOfTask(@PathVariable(value = "taskId") Long taskId) throws Exception {
        commentService.deleteByTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete comments for the user with the given userId from the comments table
    @DeleteMapping("/user/{userId}/comments")
    public ResponseEntity<List<Comment>> deleteAllCommentsOfUser(@PathVariable(value = "userId") Long userId) throws Exception {
        commentService.deleteByUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
