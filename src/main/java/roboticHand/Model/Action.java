package roboticHand.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/*
Class is the copy of the table Action in database
 */
@Entity
public class Action {

    //Identification for the table (number of servomotor on Arduino)
    @Id
    private int handAction;

    //the name of action
    private String actionLeap;
    //defines minimal value of the range for sensor
    private int leapMin;
    //defines maximal value of the range for sensor
    private int leapMax;
    //defines servomotor direction
    private int servoDirection;
    //defines minimal value of the range for servomotor on Arduino
    private int servoMin;
    //defines maximal value of the range for servomotor on Arduino
    private int servoMax;
    //defines availability for servomotor and action
    private int availability;

    //Every entity needs to have an empty controller
    public Action() {
    }

    //Getter for number of servomotor on Arduino
    public int getHandAction() {
        return handAction;
    }

    //Setter for number of servomotor on Arduino
    public void setHandAction(int handAction) {
        this.handAction = handAction;
    }

    //Getter for the name of action on Leap
    public String getActionLeap() {
        return actionLeap;
    }

    //Setter for the name of action on Leap
    public void setActionLeap(String actionLeap) {
        this.actionLeap = actionLeap;
    }

    //Getter for the minimal value of the range for sensor
    public int getLeapMin() {
        return leapMin;
    }

    //Setter for the minimal value of the range for sensor
    public void setLeapMin(int leapMin) {
        this.leapMin = leapMin;
    }

    //Getter for the maximal value of the range for sensor
    public int getLeapMax() {
        return leapMax;
    }

    //Setter for the maximal value of the range for sensor
    public void setLeapMax(int leapMax) {
        this.leapMax = leapMax;
    }

    //Getter for the servomotor direction
    public int getServoDirection() {
        return servoDirection;
    }

    //Setter for the servomotor direction
    public void setServoDirection(int servoDirection) {
        this.servoDirection = servoDirection;
    }

    //Getter for the minimal value of the range for servomotor on Arduino
    public int getServoMin() {
        return servoMin;
    }

    //Setter for the minimal value of the range for servomotor on Arduino
    public void setServoMin(int servoMin) {
        this.servoMin = servoMin;
    }

    //Getter for the maximal value of the range for servomotor on Arduino
    public int getServoMax() {
        return servoMax;
    }

    //Setter for the maximal value of the range for servomotor on Arduino
    public void setServoMax(int servoMax) {
        this.servoMax = servoMax;
    }

    //Getter for the availability for servomotor and action
    public int getAvailability() {
        return availability;
    }

    //Setter for the availability for servomotor and action
    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
