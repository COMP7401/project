import lejos.nxt.Motor;
import lejos.robotics.navigation.*;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

/**
 * Racecar.java This class connects to the remote control and moves the racecar.
 *
 * @author Harv, Callum, Kyle
 */
public class Racecar {

    private static NXTConnection remoteControlConnection;
    private static NXTConnection flagConnection;
    private static DataInputStream flagInputStream;
    private static DataInputStream remoteControlInputStream;
    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;
    public static final int CONTROLLER_DEVICE = 5;
    public static final int FLAG_DEVICE = 6;

    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        Racecar car = new Racecar();
        for (int i = 0; i < 2; i++) {
            NXTConnection connection = Bluetooth.waitForConnection();
            System.out.println("Connected to somethin");
            DataInputStream dis = connection.openDataInputStream();
            try {
                int check = dis.readInt();
                if (check == FLAG_DEVICE) {
                    car.setFlagConnection(connection);
                    car.setFlagInputStream(dis);
                    System.out.println("Connected to Flag");
                    System.out.println("");
                } else if (check == CONTROLLER_DEVICE) {
                    car.setRemoteControlConnection(connection);
                    car.setRemoteControlInputStream(dis);
                    System.out.println("Connected to Controller");
                } else {
                    System.out.println("Found nothing");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            int startFlag = flagInputStream.readInt();
            System.out.println("got the start flag");
        } catch (Exception e) {

        }
        System.out.println("HELLO!!!!!!!!");
        car.run();
    }

    /**
     *
     */
    public Racecar() {
    }

    public void setRemoteControlConnection(NXTConnection remoteControlConnection) {
        this.remoteControlConnection = remoteControlConnection;
    }

    public void setFlagConnection(NXTConnection flagConnection) {
        this.flagConnection = flagConnection;
    }

    public void setFlagInputStream(DataInputStream flagInputStream) {
        this.flagInputStream = flagInputStream;
    }

    public void setRemoteControlInputStream(DataInputStream remoteControlInputStream) {
        this.remoteControlInputStream = remoteControlInputStream;
    }

    /**
     *
     */
    public void run() {
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

    /**
     * Move forward
     */
    public void moveForward() {
        Motor.A.forward();
        Motor.C.forward();
    }

   public void stop() {
       Motor.A.stop();
       Motor.C.stop();
   }
   
   public void turnLeft() {
       Motor.A.rotate(-10,true);
       Motor.C.rotate(10,true);
   }
   
    public void turnRight() {
       Motor.A.rotate(10,true);
       Motor.C.rotate(-10,true);
   }
}
