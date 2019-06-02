package roboticHand.Controllers;

import org.junit.Test;
import roboticHand.Model.Action;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArduinoControllerTest {

    @Test
    public void sendDataWhenNotConnected() {
        String data = "{serv:0, val:1}";
        assertEquals("Not Connected", new ArduinoController().sendData(data, ""));
    }
//
//    @Test
//    public void processDataForArduinoThumbAction() {
//        List<Action> actionList = new ArrayList<>();
//        Action action = new Action();
//        action.setActionLeap("Thumb");
//        action.setHandAction(10);
//        action.setLeapMin(0);
//        action.setLeapMax(40);
//        action.setServoDirection(0);
//        action.setServoMin(0);
//        action.setServoMax(40);
//        action.setAvailability(1);
//        actionList.add(action);
//
//        String[] actions = new String[]{"Thumb"};
//        String[] values = new String[]{"40"};
//        assertEquals(150, new ArduinoController(actionList).processDataForArduino(actions, values)[0]);
//    }
//
//    @Test
//    public void processDataForArduinoLeftRightAction() {
//        List<Action> actionList = new ArrayList<>();
//        Action action = new Action();
//        action.setActionLeap("Hand Left/Right");
//        action.setHandAction(0);
//        action.setLeapMin(-300);
//        action.setLeapMax(300);
//        action.setServoDirection(0);
//        action.setServoMin(0);
//        action.setServoMax(180);
//        action.setAvailability(1);
//        actionList.add(action);
//
//        String[] actions = new String[]{"Hand Left/Right"};
//        String[] values = new String[]{"0"};
//        assertEquals(90, new ArduinoController(actionList).processDataForArduino(actions, values)[0]);
//    }
//
//    @Test
//    public void getServoNumberOfActionLeftRight() {
//        List<Action> actionList = new ArrayList<>();
//
//        Action action1 = new Action();
//        action1.setActionLeap("Thumb");
//        action1.setHandAction(10);
//        action1.setLeapMin(0);
//        action1.setLeapMax(40);
//        action1.setServoDirection(0);
//        action1.setServoMin(0);
//        action1.setServoMax(40);
//        action1.setAvailability(1);
//        actionList.add(action1);
//
//        Action action2 = new Action();
//        action2.setActionLeap("Hand Left/Right");
//        action2.setHandAction(0);
//        action2.setLeapMin(-300);
//        action2.setLeapMax(300);
//        action2.setServoDirection(0);
//        action2.setServoMin(0);
//        action2.setServoMax(180);
//        action2.setAvailability(1);
//        actionList.add(action2);
//
//        String[] actions = new String[]{"Hand Left/Right"};
//        assertEquals(0, new ArduinoController(actionList).getNormalActions(actions)[0]);
//    }
//
//    @Test
//    public void getServoNumberOfActionThumb() {
//        List<Action> actionList = new ArrayList<>();
//        Action action1 = new Action();
//        action1.setActionLeap("Thumb");
//        action1.setHandAction(10);
//        action1.setLeapMin(0);
//        action1.setLeapMax(40);
//        action1.setServoDirection(0);
//        action1.setServoMin(0);
//        action1.setServoMax(40);
//        action1.setAvailability(1);
//        actionList.add(action1);
//
//        Action action2 = new Action();
//        action2.setActionLeap("Hand Left/Right");
//        action2.setHandAction(0);
//        action2.setLeapMin(-300);
//        action2.setLeapMax(300);
//        action2.setServoDirection(0);
//        action2.setServoMin(0);
//        action2.setServoMax(180);
//        action2.setAvailability(1);
//        actionList.add(action2);
//
//        String[] actions = new String[]{"Thumb"};
//        assertEquals(10, new ArduinoController(actionList).getNormalActions(actions)[0]);
//    }
}