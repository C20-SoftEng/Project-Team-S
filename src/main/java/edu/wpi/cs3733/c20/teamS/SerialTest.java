package edu.wpi.cs3733.c20.teamS;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import gnu.io.NRSerialPort;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import static java.lang.Thread.sleep;


public class SerialTest extends Application {
    public static void sensor() throws Exception{
//      SerialPort sp = SerialPort.getCommPort("/dev/cu.usbserial-DN03ADR9");
//      sp.setComPortParameters(9600, 8, 1, 0);
//      sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
//        for(String s:NRSerialPort.getAvailableSerialPorts()){
//            System.out.println("Availible port: "+s);
//        }
        String port = "/dev/tty.usbserial-DN03ADR9";
        int baudRate = 9600;
        NRSerialPort serial = new NRSerialPort(port, baudRate);
        //Thread.sleep(800);
        serial.connect();
        Thread.sleep(800);
        DataInputStream ins = new DataInputStream(serial.getInputStream());
        //Thread.sleep(800);
//        DataOutputStream outs = new DataOutputStream(serial.getOutputStream());
        Thread.sleep(800);
//        while(ins.available() !=0){
//            Thread.sleep (10);
//        }
        int b = ins.read();


       //outs.write(b);
        String print = "Value = "+b;
        System.out.println(print);

        if( b > 51){
            System.out.println("IT'S BRIGHT MY FRIEND");


        }
        else{
            System.out.println("IT'S DARK MY FRIEND");
        }


        serial.disconnect();


    }




    public void start(Stage primaryStage) throws Exception {
        sensor();
    }

}
