import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

// class for the race car
public class End {
    // defines for the signal types the car understands
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
        // wait for a connection from the remote
        System.out.println("Waiting for remoteControl");
        NXTConnection middle = Bluetooth.waitForConnection();
        System.out.println("Connected to remoteControl");
        DataInputStream dis = middle.openDataInputStream();
        remoteControlConnection = middle;
        remoteControlInputStream = dis;
        System.out.println("input stream created");
        LCD.clear();
        System.out.println("Entering recieving signal mode");
        prepare();
        run();
    }

    // some initial setup
    public static void prepare() {
        port = SensorPort.S1;
        ultrasonicSensor = new UltrasonicSensor(port);
        Motor.A.setSpeed(Motor.A.getMaxSpeed());
        Motor.C.setSpeed(Motor.C.getMaxSpeed() - 10);
    }

    // main driver of the race car
    public static void run() {
        int pauseTime = 250;
        int turnDegree = 90;
        Sound.twoBeeps();
        try {
            // in this infinite while loop we check both the ultrasonic sensor
            // and the data input stream from the controller
            // using the inputstream.available() method we eliminate the need
            // for a second thread and can handle both sources of input in one thread
            while (true) {
                scaning();
                int bytes = remoteControlInputStream.available();
                if (bytes != 0) {
                    int command = remoteControlInputStream.readInt();
                    switch (command) {
                        case LEFT:
                            LCD.clear();
                            System.out.println("Recieved Left Signal");
                            stop();
                            //pause(pauseTime);
                            turnLeft(turnDegree);
                            System.out.println("Turing Left");
                            break;
                        case RIGHT:
                            LCD.clear();
                            System.out.println("Recieved Right Signal");
                            stop();
                           // pause(pauseTime);
                            turnRight(turnDegree);
                            System.out.println("Turing Right");
                            break;
                        case FWD:
                            LCD.clear();
                            System.out.println("Recieved FWD Signal");
                            moveForward();
                            System.out.println("Moving FWD");
                            break;
                        case STOP:
                            LCD.clear();
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

    // sets both the motors to move forward
    public static void moveForward() {
        Motor.A.forward();
        Motor.C.forward();
    }

    // sets both the motors to stop, async call to the first motor so that they
    // stop at the same time
    public static void stop() {
        Motor.A.stop(true);
        Motor.C.stop();
    }

    // turn left by the number parameter amount of degrees
    public static void turnLeft(int number) {
        Motor.A.rotate(number, true);
        Motor.C.rotate(-1 *number, true);
    }

    // turn right by the number parameter amount of degrees
    public static void turnRight(int number) {
        Motor.A.rotate(-1 * number, true);
        Motor.C.rotate(number, true);
    }

    // wrapper for Thread.sleep(time)
    public static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            // we don't want to do anything when catching this exception
            // since we just use the sleep to pause between reads on the
            // ultrasonic sensor
        }
    }

    // checks the distance on the ultrasonic sensor, called inside run()
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
