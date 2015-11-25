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

    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;
    private static NXTConnection racecarConnection;
    private static DataOutputStream racecarOutputStream;

    public static void main(String[] args) {

        System.out.println("Waiting for Flag connection");
        NXTConnection flagConnection = Bluetooth.waitForConnection();
        System.out.println("Connected to flag");
        DataInputStream dis = flagConnection.openDataInputStream();

        System.out.println("Attempting to connect to racecar");
        
        RemoteDevice end = Bluetooth.getKnownDevice("Batmobile");
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
        }
        LCD.clear();
        System.out.println("Entering remote control interface");
        run();
    }

    public static void run() {
        Sound.beep();
        System.out.println("Press the buttons");
        while (true) {
            try {
                int buttonID = Button.waitForAnyPress();
                switch (buttonID) {
                    case 2:
                        LCD.clear();
                        System.out.println("Sending Left Signal");
                        racecarOutputStream.writeInt(LEFT);
                        racecarOutputStream.flush();
                        System.out.println("Sent Left Signal");
                        break;
                    case 4:
                        LCD.clear();
                        System.out.println("Sending Right signal");
                        racecarOutputStream.writeInt(RIGHT);
                        racecarOutputStream.flush();
                        System.out.println("Sent Right Signal");
                        break;
                    case 1:
                        LCD.clear();
                        System.out.println("Sending Forward Signal");
                        racecarOutputStream.writeInt(FWD);
                        racecarOutputStream.flush();
                        System.out.println("Sent Forward Signal");
                        break;
                    case 8:
                        LCD.clear();
                        System.out.println("Sending stop Signal");
                        racecarOutputStream.writeInt(STOP);
                        racecarOutputStream.flush();
                        System.out.println("Sent stop Signal");
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                //nothing
            }
        }
    }
}
