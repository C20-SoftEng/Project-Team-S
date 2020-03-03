package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.FireWatch;
import edu.wpi.cs3733.c20.teamS.emergency.EmergencyScreen;
import gnu.io.NRSerialPort;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class FireThread extends Thread{

    int floor;
    public FireThread(int floor){
        this.floor = floor;
        this.fw = new FireWatch();
    }
    FireWatch fw;
    @Override
    public void run() {

        try{
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
            byte[] fireBall = new byte[2];

            //int b = ins.read();
            while(true){
                Thread.sleep (200);
                ins.read(fireBall);
                //System.out.println(ByteBuffer.wrap(fireBall).getChar());

                System.out.println((char) fireBall[1]);
                if((char) fireBall[1] == 'f'){
                    break;
                }
            }
            //fw.start(new Stage());
            //


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
