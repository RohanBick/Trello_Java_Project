package edu.syr.project.trelloclone.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

// table for UserTask to save the taskId and the corresponding userId assigned to the task in the database.
@Entity
@Table(name = "usertask")
public class UserTask {

    //primary key - id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    //foreign key- taskid with ManytoOne relation as many tasks can be assigned to a single user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "taskId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;

    //foreign key- userId with ManytoOne relation as many users can be assigned to single task
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    //Getter and setter methods for the fields related to UserTask

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
