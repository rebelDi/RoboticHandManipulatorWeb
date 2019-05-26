package roboticHand.Controllers;

import roboticHand.Model.Action;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

/*
Class for managing the connection and transmission to Arduino Microcontroller
 */
public class ArduinoController {
    private List<Action> dbData;

    ArduinoController() {
    }

    public ArduinoController(List<Action> dbData) {
        this.dbData = dbData;
    }

    /*
    This method sends data to Arduino
     */
    public String sendData(String dataQuery){
        try{
            byte[] b = dataQuery.getBytes();
            byte[] receiveData = new byte[1024];

            //Determines the IP address of the server on Arduino
//            InetAddress ip = InetAddress.getByName("172.20.10.3");
            InetAddress ip = InetAddress.getByName("192.168.0.123");

            //Creates a datagram based on the data of request
            DatagramSocket datagram = new DatagramSocket();
            DatagramPacket send = new DatagramPacket(b, b.length, ip, 8032);
            datagram.send(send);

            //Creates a datagram for the response of Arduino
            DatagramPacket rec = new DatagramPacket(receiveData, receiveData.length);
            datagram.setSoTimeout(6000);
            datagram.receive(rec);
            String modifiedSentence = new String(rec.getData());

            InetAddress returnIPAddress = rec.getAddress();
            int port = rec.getPort();

            //Outputs response and adress of sender
            System.out.println ("From server at: " + returnIPAddress + ":" + port);
            System.out.println("Message: '" + modifiedSentence + "'");

            modifiedSentence = modifiedSentence.replace("\u0000", "");
            datagram.close();
            return modifiedSentence;
        }catch(Exception e){
            System.out.println("Error:" + e);
        }
        return "Not Connected";
    }

    /*
    This method processes data from leap sensor for Arduino
     */
    synchronized public int[] processDataForArduino(String[] actions, String[] values){
        int[] rightValues = new int[values.length];
        int[] valuesChanged = new int[values.length];

        for(int i = 0; i < values.length; i++){
            valuesChanged[i] = Math.round(Float.parseFloat(values[i]));
        }

        for(int i = 0; i < actions.length; i++){
            Action rightInfoAction = getActionInfoByServoName(actions[i]);

            //Find the first coefficient
            float point = 180.0f / (rightInfoAction.getLeapMax() - rightInfoAction.getLeapMin());

            float val = valuesChanged[i];

            //Make the value from sensor to be within the range
            if (val > rightInfoAction.getLeapMax()){
                val = rightInfoAction.getLeapMax();
            }else if (val < rightInfoAction.getLeapMin()){
                val = rightInfoAction.getLeapMin();
            }
            float value = point * (val - rightInfoAction.getLeapMin());

            //If servomotor is directed counter-clockwise, invert the range
            if(rightInfoAction.getServoDirection() == 0){
                value = 180.0f - value;
            }

            //Find the second coefficient
            float determinant = (180.0f / (rightInfoAction.getServoMax() - rightInfoAction.getServoMin()));

            value = Math.abs(value / determinant - rightInfoAction.getServoMin());

            //This action has a weird range, so the value is corrected manually
            if(rightInfoAction.getActionLeap().equals("Hand Up/Down")){
                value -= 80;
                if(value < 80){
                    value = 80;
                }
            }

            //These actions have a weird range, so the value is corrected manually
            if(rightInfoAction.getActionLeap().equals("Middle finger") ||
                    rightInfoAction.getActionLeap().equals("Index finger") ||
                    rightInfoAction.getActionLeap().equals("Thumb")){
//                System.out.println("hi " + value);
                value += 150;
                //150 180
            }
            rightValues[i] = Math.round(value);
        }
        return rightValues;
    }

    /*
    This method gets the action information by the name of the action
     */
    private Action getActionInfoByServoName(String name){
        for (Action dbDatum : dbData) {
            if (dbDatum.getActionLeap().equals(name)) {
                return dbDatum;
            }
        }
        return null;
    }

    /*
    This method gets numbers of servomotors by name of actions
     */
    public int[] getNormalActions(String[] actions) {
        int[] acts = new int[actions.length];

        for(int i = 0; i < acts.length; i++) {
            boolean flag = false;
            for (Action dbDatum : dbData) {
                if (dbDatum.getActionLeap().equals(actions[i])) {
                    acts[i] = dbDatum.getHandAction();
                    flag = true;
                }
            }
            if(!flag){
                acts[i] = -100;
            }
        }
        return acts;
    }

    /*
    This method checks if all actions were correctly written from database
     */
    public boolean checkActions(int[] actions){
        for (int action : actions) {
            if (action == -100) {
                return false;
            }
        }
        return true;
    }
}
