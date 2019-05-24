package roboticHand.Model;

/*
Class for creating the right model for data transmitted to Arduino
 */
public class ArduinoData {
    private int serv;
    private int val;

    public ArduinoData(int servoName, int value) {
        this.serv = servoName;
        this.val = value;
    }

    public int getServ() {
        return serv;
    }

    public void setServ(int serv) {
        this.serv = serv;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
