package roboticHand.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
Class is the copy of the table Question in database
 */
@Entity
public class Question {

    //Identification for the table
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //defines login of user
    private String user;
    //defines question of user
    private String question;
    //defines answer for user's question
    private String answer;
    //defines status of question
    private String status;

    //Getter for the id of user
    public Integer getId() {
        return id;
    }

    //Setter for the id of user
    public void setId(Integer id) {
        this.id = id;
    }

    //Getter for the login of user
    public String getUser() {
        return user;
    }

    //Setter for the login of user
    public void setUser(String user) {
        this.user = user;
    }

    //Getter for the question of user
    public String getQuestion() {
        return question;
    }

    //Setter for the question of user
    public void setQuestion(String question) {
        this.question = question;
    }

    //Getter for the answer for user's question
    public String getAnswer() {
        return answer;
    }

    //Setter for the answer for user's question
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //Getter for the status of question
    public String getStatus() {
        return status;
    }

    //Setter for the status of question
    public void setStatus(String status) {
        this.status = status;
    }
}
