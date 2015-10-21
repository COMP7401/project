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
    private NXTConnection racecarConnection;
    private NXTConnection flagConnection;
    private DataInputStream flagInputStream;
    private DataInputStream racecarInputStream;
    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;
    public static final int CONTROLLER_DEVICE = 5;
    public static final int FLAG_DEVICE  = 6;

    
    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        Racecar car = new Racecar();
        for(int i = 0; i < 2; i++) {
            NXTConnection connection = Bluetooth.waitForConnection();
            DataInputStream dis = connection.openDataInputStream();
            try {
                int check = dis.readInt();
                if(check == FLAG_DEVICE) {
                    car.setFlagConnection(connection);
                    car.setFlagInputStream(dis);
                    System.out.println("Connected to Flag");
                } else if(check == CONTROLLER_DEVICE) {
                    car.setRacecarConnection(connection);
                    car.setRacecarInputStream(dis);
                    System.out.println("Connected to Controller");
                }
            } catch(Exception e) {
                
            }
        }    
        car.run();
    }
    
    /**
     * 
     */
    public Racecar() {}
    
    public void setRacecarConnection(NXTConnection racecarConnection) {
        this.racecarConnection = racecarConnection;
    }

    public void setFlagConnection(NXTConnection flagConnection) {
        this.flagConnection = flagConnection;
    }

    public void setFlagInputStream(DataInputStream flagInputStream) {
        this.flagInputStream = flagInputStream;
    }

    public void setRacecarInputStream(DataInputStream racecarInputStream) {
        this.racecarInputStream = racecarInputStream;
    }
    
    
    /**
     * 
     */
    public void run() {
        Motor.A.setSpeed(360);
        Motor.C.setSpeed(360);

        while(true) {
            moveForward();
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
