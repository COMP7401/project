import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

public class End {

    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;

    public static void main(String[] args) {
       
        NXTConnection middle = Bluetooth.waitForConnection();
        System.out.println("Connected to remoteControl");
        DataInputStream dis = middle.openDataInputStream();
        System.out.println("input stream created");
        int reading = -1;
        try {
            System.out.println("About to read signal");
            reading = dis.readInt();
            System.out.println("Recived signall");
            
        } catch(Exception E) {
            System.out.println("Didnt Read");
        }
        System.out.println("Waiting for end of program");
        Button.waitForPress();
        if (reading == FLAG_SIGNAL) {
             run();
        }
    }

    public static void run() {
        Motor.A.setSpeed(360);
        Motor.C.setSpeed(360);
        moveForward();
    }
    
    public static void moveForward() {
        Motor.A.forward();
        Motor.C.forward();
    }

    public void stop() {
        Motor.A.stop();
        Motor.C.stop();
    }

    public void turnLeft() {
        Motor.A.rotate(-10, true);
        Motor.C.rotate(10, true);
    }

    public void turnRight() {
        Motor.A.rotate(10, true);
        Motor.C.rotate(-10, true);
    }
}
