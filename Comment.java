package edu.syr.project.trelloclone.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // Define Many-to-One relationship with Task entity
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "taskId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;

    // Define Many-to-One relationship with User entity
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Lob
    private String Comment; // The required Field to store the comment text in the database

    public Comment() {
        // Default constructor as follows
    }

    // Getters and Setters for the 'id' field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters and Setters for the 'user' field
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getters and Setters for the 'task' field
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    // Getters and Setters for the 'comment' field
    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        this.Comment = comment;
    }
}
