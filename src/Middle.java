import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import javax.bluetooth.*;
import java.io.*;

// Class for the controller
public class Middle {
    // defines for the signals the race car will understand
    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;
    private static NXTConnection racecarConnection;
    private static DataOutputStream racecarOutputStream;

    public static void main(String[] args) {
        // wait for the flag to connect
        System.out.println("Waiting for Flag connection");
        NXTConnection flagConnection = Bluetooth.waitForConnection();
        System.out.println("Connected to flag");
        DataInputStream dis = flagConnection.openDataInputStream();

        System.out.println("Attempting to connect to racecar");
        // make a connection to the car
        RemoteDevice end = Bluetooth.getKnownDevice("Batmobile");
        if (end == null) {
            System.out.println("No Such device existed");
            Button.waitForAnyPress();
            System.exit(1);
        }
        BTConnection endConnection = Bluetooth.connect(end);

        if (endConnection == null) {
            System.out.println("Connection Failed");
            Button.waitForAnyPress();
            System.exit(1);
        } else {
            System.out.println("Connected to Racecar");
        }

        DataOutputStream dos = endConnection.openDataOutputStream();
        racecarConnection = endConnection;
        racecarOutputStream = dos;
        System.out.println("output stream created");

        int reading = 0;
        try {
            System.out.println("Ready for Flag Signal");
            reading = dis.readInt();
            System.out.println("Got the Flag signal ");
            dis.close();
        } catch (IOException E) {
            System.out.println("Could not read flag signal");
            Button.waitForAnyPress();
            System.exit(1);
        }
        LCD.clear();
        System.out.println("Entering remote control interface");
        run();
    }

    // main driver of the controller, called after the initial setup in main
    public static void run() {
        Sound.beep();
        System.out.println("Press the buttons");
        while (true) {
            try {
                int buttonID = Button.waitForAnyPress();
                // switch case on the button type we pressed
                switch (buttonID) {
                    case 2: // left button
                        LCD.clear();
                        System.out.println("Sending Left Signal");
                        racecarOutputStream.writeInt(LEFT);
                        racecarOutputStream.flush();
                        System.out.println("Sent Left Signal");
                        break;
                    case 4: // right button
                        LCD.clear();
                        System.out.println("Sending Right signal");
                        racecarOutputStream.writeInt(RIGHT);
                        racecarOutputStream.flush();
                        System.out.println("Sent Right Signal");
                        break;
                    case 1: // middle button, forward
                        LCD.clear();
                        System.out.println("Sending Forward Signal");
                        racecarOutputStream.writeInt(FWD);
                        racecarOutputStream.flush();
                        System.out.println("Sent Forward Signal");
                        break;
                    case 8: // bottom button, stop
                        LCD.clear();
                        System.out.println("Sending stop Signal");
                        racecarOutputStream.writeInt(STOP);
                        racecarOutputStream.flush();
                        System.out.println("Sent stop Signal");
                        break;
                    default: // there shouldn't be any other button types
                        break;
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e);
                Button.waitForAnyPress();
                System.exit(1);
            }
        }
    }
}
