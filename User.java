package edu.syr.project.trelloclone.data.models;

import javax.persistence.*;

// table for User to save user details in the database.
@Entity
@Table(name="user")
public class User {

    // primary key - userId to have a id for each user
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long userId;

    //name field to save User's name in database
    @Column(name = "name")
    private String name;

    //Default user constructor for User
    public User() {
    }

    //Getter and setter methods for all the fields related to User
    public User(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
