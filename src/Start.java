import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

public class Start {
    public static final int FLAG_SIGNAL = 4;
    public static void main(String[] args) {
        RemoteDevice middle = Bluetooth.getKnownDevice("KAITO");
        //RemoteDevice middle2;
        //RemoteDevice middle3;
        if (middle == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }
        BTConnection middleConnection = Bluetooth.connect(middle);
        System.out.println("Connected to Middle");
        DataOutputStream dos = middleConnection.openDataOutputStream();
        System.out.println("Created out Stream");
//        try {
//           // System.out.println("Waiting 30 seconds");
//            //Thread.sleep(30000);
//       
//        } catch(Exception E) {
//        
//        }
        System.out.println("Waited 30 seconds | Press button for signal");
        Button.waitForPress();
        try {
            System.out.println("Sending Flag signal to Middle");
            dos.writeInt(FLAG_SIGNAL);
            dos.flush();
            System.out.println("Flag signal sent to Middle");
        } catch(IOException E) {
        
        }
        Button.waitForPress();
    }
}
