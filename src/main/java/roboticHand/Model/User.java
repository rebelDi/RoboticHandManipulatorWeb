package roboticHand.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
Class is the copy of the table User in database
 */
@Entity
public class User {

    //Identification for the table
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //defines login of user
    private String login;
    //defines user's password
    private String password;
    //defines name of user
    private String name;
    //defines surname of user
    private String surname;
    //defines rights of user
    private Character rights;
    //defines secret question
    private String secretQuestion;
    //defines secret answer
    private String secretAnswer;

    //Every entity needs to have an empty controller
    public User() {
    }

    //Getter for the login of user
    public String getLogin() {
        return login;
    }

    //Setter for the login of user
    public void setLogin(String login) {
        this.login = login;
    }

    //Getter for the user's password
    public String getPassword() {
        return password;
    }

    //Setter for the user's password
    public void setPassword(String password) {
        this.password = password;
    }

    //Getter for the name of user
    public String getName() {
        return name;
    }

    //Setter for the name of user
    public void setName(String name) {
        this.name = name;
    }

    //Getter for the surname of user
    public String getSurname() {
        return surname;
    }

    //Setter for the surname of user
    public void setSurname(String surname) {
        this.surname = surname;
    }

    //Getter for the rights of user
    public Character getRights() {
        return rights;
    }

    //Setter for the rights of user
    public void setRights(Character rights) {
        this.rights = rights;
    }

    //Getter for the secret question
    public String getSecretQuestion() {
        return secretQuestion;
    }

    //Setter for the secret question
    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    //Getter for the secret answer
    public String getSecretAnswer() {
        return secretAnswer;
    }

    //Setter for the secret answer
    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }
}
