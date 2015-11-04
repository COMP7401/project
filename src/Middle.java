import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

public class Middle {
	public static void main(String[] args) {
     	System.out.println("Waiting for Flag connection");
        NXTConnection flagConnection = Bluetooth.waitForConnection();   
        DataInputStream dis = flagConnection.openDataInputStream();
        System.out.println("Connected to flag");

		RemoteDevice end =  Bluetooth.getKnownDevice("Batmobile");
		if (end == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }	
        BTConnection endConnection = Bluetooth.connect(end);

        if (endConnection == null) {
            System.out.println("Connection Failed");
            System.exit(1);
        } else {
            System.out.println("Connected to Racecar");
        }

        DataOutputStream dos = endConnection.openDataOutputStream();
        System.out.println("output stream created");
     	Button.waitForPress();	
	}
}