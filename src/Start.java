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
	public static void main(String[] args) {
		RemoteDevice middle =  Bluetooth.getKnownDevice("KAITO");
		if (middle == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }	
        BTConnection middleConnection = Bluetooth.connect(middle);
        DataOutputStream dos = middleConnection.openDataOutputStream();
        Button.waitForPress();
	}
}