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

    private NXTConnection remoteControlConnection;
    private DataInputStream remoteControlInputStream;
    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;
    public static final int CONTROLLER_DEVICE = 5;

    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        Racecar car = new Racecar();
        int check = -1;
        NXTConnection connection = Bluetooth.waitForConnection();
        System.out.println("Connected to remoteControl");
        DataInputStream dis = connection.openDataInputStream();
        try {
            check = dis.readInt();
            if (check == CONTROLLER_DEVICE) {
                car.setRemoteControlConnection(connection);
                car.setRemoteControlInputStream(dis);
            } else {
                System.out.println("Did not find remote");
            }
        } catch (Exception e) {
            //nothing
        }

        //Flag signal to get moving
        //Had to add this second flag signal B/C the car would start moving as soon as the remote connected
        try {
            check = dis.readInt();
            if (check == FLAG_SIGNAL) {
                System.out.println("Recived flag signal, ready 2 go");
                car.run();
            } else {
                System.out.println("No flag signal");
            }
        } catch (Exception e) {
            //nothing
        }
    }

    /**
     *
     */
    public Racecar() {
    }

    public void setRemoteControlConnection(NXTConnection remoteControlConnection) {
        this.remoteControlConnection = remoteControlConnection;
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
        Motor.A.rotate(-10, true);
        Motor.C.rotate(10, true);
    }

    public void turnRight() {
        Motor.A.rotate(10, true);
        Motor.C.rotate(-10, true);
    }
}
