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
        System.out.println("Connecting to RemoteControl");
        RemoteDevice middle  = Bluetooth.getKnownDevice("NXT007");
        //RemoteDevice middle2 = Bluetooth.getKnownDevice("KyleCal");
       // RemoteDevice middle3 = Bluetooth.getKnownDevice("Bender");
        if (middle == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }
        BTConnection middleConnection  = Bluetooth.connect(middle);
       // BTConnection middleConnection2 = Bluetooth.connect(middle2);
        //BTConnection middleConnection3 = Bluetooth.connect(middle3);
        
        System.out.println("Connected to RemoteControl");
        DataOutputStream dos  = middleConnection.openDataOutputStream();
       // DataOutputStream dos2 = middleConnection2.openDataOutputStream();
       // DataOutputStream dos3 = middleConnection3.openDataOutputStream();
        LCD.clear();
        System.out.println("Press button to send signal");
        Button.waitForAnyPress();
        try {
            System.out.println("Sending Flag signal to RemoteControl");
            dos.writeInt(FLAG_SIGNAL);
            System.out.println("Sent Signal to NXT007");
           // dos2.writeInt(FLAG_SIGNAL);
            System.out.println("Sent Signal to KyleCal");
            //dos3.writeInt(FLAG_SIGNAL);
            System.out.println("Sent Signal to Bender");
            dos.flush();
           // dos2.flush();
            //dos3.flush();
            Sound.beepSequence();
            LCD.clear();
            System.out.println("Flag signal sent to RemoteControl, Press button to end.");
        } catch (IOException E) {
            System.out.println("Could not send Flag signal");
        }
        Button.waitForAnyPress();
    }
}
