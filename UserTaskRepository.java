package edu.syr.project.trelloclone.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.syr.project.trelloclone.data.models.Comment;
import edu.syr.project.trelloclone.data.models.Task;
import edu.syr.project.trelloclone.data.models.User;
import edu.syr.project.trelloclone.data.models.UserTask;

import javax.transaction.Transactional;
import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

    //Returns  taskId and userId with the specified task details
    List<Comment> findByTask(Task task);

    //Returns  taskId and userId with the specified user details
    List<Comment> findByUser(User user);


    //Returns user name, user id, task description and taskid
    @Query(value = "SELECT rohanown.user.name,rohanown.usertask.user_id,rohanown.task.description," +
            "rohanown.usertask.task_id FROM rohanown.usertask \n" +
            "INNER JOIN rohanown.user On \n" +
            "rohanown.user.user_id = rohanown.usertask.user_id \n" +
            "INNER JOIN rohanown.task ON\n" +
            " rohanown.task.task_id = rohanown.usertask.task_id;",
            nativeQuery = true)
    List<Object> getUserTaskDetailsWithDescription();
    

    //Returns userid,taskid and comments on the task
    @Query(value = "SELECT DISTINCT rohanown.usertask.user_id,\n" +
            "            rohanown.usertask.task_id,rohanown.comment.comment FROM rohanown.usertask\n" +
            "            INNER JOIN rohanown.comment On \n" +
            "\t\trohanown.usertask.user_id = rohanown.comment.user_id \n" +
            "            INNER JOIN rohanown.task ON\n" +
            "\t\trohanown.usertask.task_id = rohanown.comment.task_id;",nativeQuery = true)
    List<Object> getCommentsWithUserIdAndTaskId();
    
    

    //Deletes taskId and userId with the specified task details
    @Transactional
    UserTask deleteByTask(Task task);

    //Deletes taskId and userId with the specified user details
    @Transactional
    void deleteByUser(User user);



}
