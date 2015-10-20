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
 * Racecar.java
 * This class connects to the remote control and moves the racecar. 
 * 
 * @author Harv, Callum, Kyle
 */
public class Racecar {
    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        
        Racecar car = new Racecar();
        car.run();
    }
    
    public Racecar() {
    
    }
    
    public void run() {
        Motor.A.setSpeed(360);
        Motor.C.setSpeed(360);
        this.waitForFlag();
        while(true) {
            moveForward();
        }
        

    }
    
    /**
     * Waiting until the flag is connected.
     */
    public void waitForFlag() {
        NXTConnection connection = Bluetooth.waitForConnection();
        System.out.println("Connected to Flag");
        DataInputStream dis = connection.openDataInputStream();
        try {
            int a = dis.readInt();
        } catch (Exception ex) {
            // nothing
        }
    }
    
    /**
     * Move forward
     */
    public void moveForward() {
        Motor.A.forward();
        Motor.C.forward();
    }
    
    
}
