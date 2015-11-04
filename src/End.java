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
    private static NXTConnection remoteControlConnection;
    private static DataInputStream remoteControlInputStream;
    public static void main(String[] args) {
       
        NXTConnection middle = Bluetooth.waitForConnection();
        System.out.println("Connected to remoteControl");
        DataInputStream dis = middle.openDataInputStream();
        remoteControlConnection = middle;
        remoteControlInputStream = dis;
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

        if (reading == FLAG_SIGNAL) {
             run();
        }
    }

    public static void run() {
        Motor.A.setSpeed(360);
        Motor.C.setSpeed(360);
        moveForward();
        try {
            while (true) {
                int bytes = remoteControlInputStream.available();
                if (bytes != 0) {
                    int command = remoteControlInputStream.readInt();
                    switch (command) {
                        case LEFT:
                            System.out.println("Recieved Left Signal");
                            turnLeft();
                            System.out.println("Turing Left");
                            break;
                        case RIGHT:
                            System.out.println("Recieved Right Signal");
                            turnRight();
                            System.out.println("Turing Right");
                            break;
                        case FWD:
                            System.out.println("Recieved FWD Signal");
                            moveForward();
                            System.out.println("Moving FWD");
                            break;
                        case STOP:
                            System.out.println("Recieved Stop Signal");
                            stop();
                            System.out.println("stop");
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            //nothing
        }
      
    }
    
    public static void moveForward() {
        Motor.A.forward();
        Motor.C.forward();
    }

    public static void stop() {
        Motor.A.stop();
        Motor.C.stop();
    }

    public static void turnLeft() {
        Motor.A.rotate(-10, true);
        Motor.C.rotate(10, true);
    }

    public static void turnRight() {
        Motor.A.rotate(10, true);
        Motor.C.rotate(-10, true);
    }
}
