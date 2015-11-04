import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

public class End {
	public static void main(String[] args) {
		NXTConnection middle =  Bluetooth.waitForConnection();
        System.out.println("Connected to remoteControl");
        DataInputStream dis = middle.openDataInputStream();
        System.out.println("input stream created");
        Button.waitForPress();
	}
}