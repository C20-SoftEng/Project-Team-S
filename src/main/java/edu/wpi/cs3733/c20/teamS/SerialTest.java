package edu.wpi.cs3733.c20.teamS;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import gnu.io.NRSerialPort;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static java.lang.Thread.sleep;


public class SerialTest extends Application{

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
        short b = 0;
        char c = 'o';
        //byte[] buffer = new byte[2];
        byte[] fireBall = new byte[2];
        //System.out.println("Bytes avalible: " + ins.available());
        int i = 0;
        //ByteBuffer bb = ByteBuffer
        //byte[] biteArray = new byte[2];
        while(i<100){
            //System.out.println(ins.available());
            Thread.sleep (200);
            ins.read(fireBall);
            //System.out.println(ByteBuffer.wrap(fireBall).getChar());
            System.out.println((char) fireBall[1]);

            i++;
        }
       //outs.write(b);
        String print = "Value = "+b;
        System.out.println(print);

        if( b > 51){
            System.out.println("IT'S BRIGHT MY FRIEND");



        }
        else{
            System.out.println("IT'S DARK MY FRIEND");
        }





    }




    public void start(Stage primaryStage) throws Exception {
        //sensor();
        runSensor();
    }

    public static boolean runSensor(){
        String port = "/dev/tty.usbserial-DN03ADR9";
        int baudRate = 9600;
        NRSerialPort serial = new NRSerialPort(port, baudRate);
        try {


            //Thread.sleep(800);
            serial.connect();
            //Thread.sleep(800);
            DataInputStream ins = new DataInputStream(serial.getInputStream());
            //Thread.sleep(800);
//        DataOutputStream outs = new DataOutputStream(serial.getOutputStream());
            //Thread.sleep(800);
//        while(ins.available() !=0){
//            Thread.sleep (10);
//        }
            byte[] fireBall = new byte[2];

            //int b = ins.read();
//            while(true){
//                Thread.sleep (200);
//
//                //System.out.println(ByteBuffer.wrap(fireBall).getChar());
//
//                System.out.println((char) fireBall[1]);

//            }
            while(true){

                ins.read(fireBall);

                System.out.println((char) fireBall[0]);
                if (((char) fireBall[0] == 'f') || (char) fireBall[0] == 's') {
                    break;
                }
            }

            //System.out.println((char) fireBall[0]);
            if ((char) fireBall[0] == 'f') {
                serial.disconnect();
                return true;
            }
            else {
                serial.disconnect();
                return false;
            }
            //fw.start(new Stage());
            //


        } catch (Exception e) {
            e.printStackTrace();
        }
        serial.disconnect();
        return false;
    }




}
