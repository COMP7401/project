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
 * Flag.java This class is used to tell the Racecar to start moving.
 *
 * @author Harv, Callum, Kyle
 */
public class Flag {
     public static final int FLAG_DEVICE  = 6;
     public static final int FLAG_SIGNAL = 4;
    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        Flag flag = new Flag();
        
        RemoteDevice racecar = Bluetooth.getKnownDevice("Batmobile");
//        RemoteDevice racecar2 = Bluetooth.getKnownDevice("?");
//        RemoteDevice racecar3 = Bluetooth.getKnownDevice("?");
        
        if (racecar == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }
        BTConnection racecarConnection = Bluetooth.connect(racecar);
       // BTConnection racecarConnection2 = Bluetooth.connect(racecar2);
       // BTConnection racecarConnection3 = Bluetooth.connect(racecar3);
        
        if (racecarConnection == null) {
            System.out.println("Connection Failed");
            System.exit(1);
        }
       DataOutputStream dos = racecarConnection.openDataOutputStream();
        
        try {
            dos.writeInt(FLAG_DEVICE);
            dos.flush();
            Button.waitForPress();
            dos.writeInt(FLAG_SIGNAL);
            dos.flush();
            //add three data output streams
            
        } catch(Exception ex) {
            //Do nothing
        }
    }

    public void run() {

    }
}