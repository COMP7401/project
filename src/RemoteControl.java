import lejos.nxt.Motor;
import lejos.robotics.navigation.*;
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
 * RemoteControl.java This class is used to control the racecar.
 *
 * @author Harv, Callum, Kyle
 */
public class RemoteControl {

    public static final int FWD = 0;
    public static final int STOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FLAG_SIGNAL = 4;
    public static final int CONTROLLER_DEVICE = 5;
    private NXTConnection racecarConnection;
    private DataOutputStream racecarOutputStream;
    private Button leftButton;
    private Button rightButton;
    private Button centerButton;
    private Button bottomButton;

    /**
     * Main entry to the program.
     */
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();

        RemoteDevice racecar = Bluetooth.getKnownDevice("Batmobile");

        if (racecar == null) {
            System.out.println("No Such device existed");
            System.exit(1);
        }
        BTConnection racecarConnection = Bluetooth.connect(racecar);

        if (racecarConnection == null) {
            System.out.println("Connection Failed");
            System.exit(1);
        } else {
            System.out.println("Connected to Racecar");
        }

        DataOutputStream dos = racecarConnection.openDataOutputStream();
        try {
            System.out.println("Sending controller signal");
            dos.writeInt(CONTROLLER_DEVICE);
            dos.flush();
            System.out.println("Sent Controller signal");
        } catch (Exception ex) {
            //Do nothing
        }
        remote.setRacecarConnection(racecarConnection);
        remote.setRacecarOutputStream(dos);
        
        
        //Waiting for flag to set us off here
        System.out.println("Waiting for Flag connection");
        NXTConnection flagConnection = Bluetooth.waitForConnection();
        System.out.println("Connected to flag");
        DataInputStream dis = flagConnection.openDataInputStream();
        try {
            int check = dis.readInt();
            if (check == FLAG_SIGNAL) {
                System.out.println("Recived flag signal");
                dos.writeInt(FLAG_SIGNAL);
                dos.flush();
                System.out.println("sent flag signal to racecar");
                dis.close();
                remote.run();
            } else {
                System.out.println("Did not recieve flag connection");
            }

        } catch (Exception e) {

        }
    }

    public void setRacecarConnection(NXTConnection racecarConnection) {
        this.racecarConnection = racecarConnection;
    }

    public void setRacecarOutputStream(DataOutputStream racecarOutputStream) {
        this.racecarOutputStream = racecarOutputStream;
    }

    public RemoteControl() {
        leftButton = Button.LEFT;
        rightButton = Button.RIGHT;
        centerButton = Button.ENTER;
        bottomButton = Button.ESCAPE;
    }

    public void run() {
        while (true) {
            try {
                int buttonID = Button.waitForPress();
                switch (buttonID) {
                    case 2:
                        racecarOutputStream.writeInt(LEFT);
                        racecarOutputStream.flush();
                        System.out.println("Sent Left Signal");
                        break;
                    case 4:
                        racecarOutputStream.writeInt(RIGHT);
                        racecarOutputStream.flush();
                        System.out.println("Sent Right Signal");
                        break;
                    case 1:
                        System.out.println("Sending Forward Signal");
                        racecarOutputStream.writeInt(FWD);
                        racecarOutputStream.flush();
                        System.out.println("Sent Forward Signal");
                        break;
                    case 8:
                        racecarOutputStream.writeInt(STOP);
                        racecarOutputStream.flush();
                        System.out.println("Sent stop Signal");
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                //nothing
            }

        }
    }
}
