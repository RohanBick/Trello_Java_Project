package edu.syr.project.trelloclone.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.syr.project.trelloclone.data.models.*;
import edu.syr.project.trelloclone.data.repository.CommentRepository;
import edu.syr.project.trelloclone.data.repository.HistoryRepository;
import edu.syr.project.trelloclone.data.repository.TaskRepository;
import edu.syr.project.trelloclone.data.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    public List<Comment> getAllCommentsByTaskId(long taskId) throws Exception {
        if (!taskRepository.existsById(taskId)) {
            throw new Exception("Could not find the task with id = " + taskId);
        }
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Could not find task!"));

        List<Comment> comments = commentRepository.findByTask(task);
        return comments;
    }

    public Comment createComment(long taskId, CommentSchema commentRequest) throws Exception {
        User user = userRepository.findById(commentRequest.userId)
                .orElseThrow(() -> new Exception("Could not find any User with id = " + commentRequest.userId));
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment);
        comment.setUser(user);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Could not find Task with id = " + taskId));

        comment.setTask(task);
        Comment savedComment = commentRepository.save(comment);

        Task taskForHistory = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Could not find Task with id = " + taskId));

        History history = new History();
        history.setTask(taskForHistory);
        history.setChange_id(savedComment.getId());
        history.setChange_id_type("Comment id");
        history.setChange_description("The Comment was added by " + user.getName() + " : " + savedComment.getComment());
        history.setUpdated_on(LocalDateTime.now());
        historyRepository.save(history);

        return savedComment;
    }

    public List<Comment> getAllCommentsByUserId(long userId) throws Exception {
        if (!userRepository.existsById(userId)) {
            throw new Exception("Could not find User with id = " + userId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Could not find any comments with the id"));

        List<Comment> comments = commentRepository.findByUser(user);
        return comments;
    }

    public void deleteComment(long id) throws Exception {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Could not find comment with id = " + id));

        Task task = taskRepository.findById(comment.getTask().getTaskId())
                .orElseThrow(() -> new Exception("Could not find Task for this comment"));

        History history = new History();
        history.setTask(task);
        history.setChange_id(comment.getId());
        history.setChange_id_type("Comment id");
        history.setChange_description("Following Comment " + comment.getComment() + " was deleted by user : " + comment.getUser().getName());
        history.setUpdated_on(LocalDateTime.now());
        historyRepository.save(history);

        commentRepository.deleteById(id);
    }

    public Comment getCommentsByCommentId(Long id) throws Exception {
        return commentRepository.findById(id)
                .orElseThrow(() -> new Exception("could not find any Comment with id = " + id));
    }

    public void deleteByTask(Long taskId) throws Exception {
        if (!taskRepository.existsById(taskId)) {
            throw new Exception("could not find any Task with id = " + taskId);
        }
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("could not find any Task with id = " + taskId));

        commentRepository.deleteByTask(task);
    }

    public void deleteByUser(Long userId) throws Exception {
        if (!userRepository.existsById(userId)) {
            throw new Exception("could not find any User with id = " + userId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("could not find any Task with id = " + userId));

        commentRepository.deleteByUser(user);
    }
}
