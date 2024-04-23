package edu.syr.project.trelloclone.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

//Created a table for history to save the taskId and the corresponding history for the task in the database
@Entity
@Table(name = "history")
public class History {

    // primary key - id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    //foreign key - taskId with ManytoOne relation as many tasks can be assigned to a single user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "taskId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;

    //change id to save the id of the entity field to be modified
    @Column(name = "change_id")
    private long change_id;

    //change id-type to save the name of the entity field to be modified
    @Column(name = "change_id_type")
    private String change_id_type;

    //Getters and Setters as follows: containing above variables.

    public String getChange_id_type() {
        return change_id_type;
    }

    public void setChange_id_type(String change_id_type) {
        this.change_id_type = change_id_type;
    }

    @Column(name = "change_description")
    private String change_description;

    @Column(name = "updated_on")
    private LocalDateTime updated_on;

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

    public long getChange_id() {
        return change_id;
    }

    public void setChange_id(long change_id) {
        this.change_id = change_id;
    }

    public String getChange_description() {
        return change_description;
    }

    public void setChange_description(String change_description) {
        this.change_description = change_description;
    }

    public LocalDateTime getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(LocalDateTime updated_on) {
        this.updated_on = updated_on;
    }
}
