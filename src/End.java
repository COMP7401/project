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
    private static SensorPort port;
    private static UltrasonicSensor ultrasonicSensor;
    public static void main(String[] args) {
        System.out.println("Waiting for remoteControl");
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
            
        } catch(IOException E) {
            //nothing
        } 
       
        if (reading == FLAG_SIGNAL) {
             System.out.println("Entering recieving signal mode");
             prepare();
             run();
        }
    }
    public static void prepare() {
        port = SensorPort.S1;
        ultrasonicSensor = new UltrasonicSensor(port);
        Motor.A.setSpeed(360);
        Motor.C.setSpeed(360);
    }
    public static void run() {
        int pauseTime = 250;
        int turnDegree = 90;
        moveForward();
        try {
            while (true) {
                scaning();
                int bytes = remoteControlInputStream.available();
                if (bytes != 0) {
                    int command = remoteControlInputStream.readInt();
                    switch (command) {
                        case LEFT:
                            System.out.println("Recieved Left Signal");
                            stop();
                            //pause(pauseTime);
                            turnLeft(turnDegree);
                            System.out.println("Turing Left");
                            break;
                        case RIGHT:
                            System.out.println("Recieved Right Signal");
                            stop();
                           // pause(pauseTime);
                            turnRight(turnDegree);
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
                            System.out.println("Stopped");
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
        Motor.A.stop(true);
        Motor.C.stop();
    }

    public static void turnLeft(int number) {
        Motor.A.rotate(number, true);
        Motor.C.rotate(-1 *number, true);
    }

    public static void turnRight(int number) {
        Motor.A.rotate(-1 * number, true);
        Motor.C.rotate(number, true);
    }
    
    public static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
        
        }
    }
    
    public static void scaning() {
        int max = 30;
        int distance = 0;
        pause(100);
        distance = ultrasonicSensor.getDistance();
        if (distance < max) {
            System.out.println("Stopping because of scanner");
            stop();
        }
    }
}
