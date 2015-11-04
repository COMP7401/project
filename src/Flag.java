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

    public static final int FLAG_SIGNAL = 4;

    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        Flag flag = new Flag();

        RemoteDevice remote = Bluetooth.getKnownDevice("KAITO");
//        RemoteDevice racecar2 = Bluetooth.getKnownDevice("?");
//        RemoteDevice racecar3 = Bluetooth.getKnownDevice("?");

        if (remote == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }
        System.out.println("Testing connection");
        BTConnection remoteConnection = Bluetooth.connect(remote);
        System.out.println("Connected to remote");
       // BTConnection racecarConnection2 = Bluetooth.connect(racecar2);
        // BTConnection racecarConnection3 = Bluetooth.connect(racecar3);

        if (remoteConnection == null) {
            System.out.println("Connection Failed");
            System.exit(1);
        }
        
        System.out.println("Getting ready to output signal");
        DataOutputStream dos = remoteConnection.openDataOutputStream();
        //DataOutputStream dos2 = remoteConnection2.openDataOutputStream();
        //DataOutputStream dos3 = remoteConnection3.openDataOutputStream();
        
        try {
            System.out.println("Sending Flag signal to Remote Control");
            dos.writeInt(FLAG_SIGNAL);
            dos.flush();
            System.out.println("Flag signal sent to remote control");
            //add three data output streams

        } catch (Exception ex) {
            //Do nothing
        }
    }

    public void run() {

    }
}
