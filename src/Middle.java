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
    private NXTConnection racecarConnection;
    private DataOutputStream racecarOutputStream;

    public static void main(String[] args) {
        
        System.out.println("Waiting for Flag connection");
        NXTConnection flagConnection = Bluetooth.waitForConnection();
        System.out.println("Connected to flag");
        DataInputStream dis = flagConnection.openDataInputStream();

        System.out.println("attempting to connect to racecar");
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
        int reading = 0;
        try {
            System.out.println("About to Read Signal");
            reading = dis.readInt();
            System.out.println("Read signal ");
        } catch (IOException E) {

        }

        DataOutputStream dos = endConnection.openDataOutputStream();
        System.out.println("output stream created");

        try {
            System.out.println("Sending Flag signal to racecar");
            dos.writeInt(reading);
            dos.flush(); 
            System.out.println("Sent flag singal to racecar");
        } catch (IOException E) {

        }
        Button.waitForPress();
    }
    
     public void setRacecarConnection(NXTConnection racecarConnection) {
        this.racecarConnection = racecarConnection;
    }

    public void setRacecarOutputStream(DataOutputStream racecarOutputStream) {
        this.racecarOutputStream = racecarOutputStream;
    }
}