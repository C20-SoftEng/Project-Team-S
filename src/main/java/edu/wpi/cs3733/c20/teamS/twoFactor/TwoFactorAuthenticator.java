package edu.wpi.cs3733.c20.teamS.twoFactor;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.utilities.Mailer;

import java.util.Random;


public class TwoFactorAuthenticator {

    public EmployeeData ed;
    public String carrier;
    public int code;

    public TwoFactorAuthenticator(EmployeeData ed, String carrier, int code) {
        this.ed = ed;
        this.carrier = carrier;
        this.code = code;
    }

    public static int generateCode(){
        Random rand = new Random();
        return rand.nextInt(999999);
    }

    public int runTextTFA(EmployeeData ed, String carrier, int tfaCode){
        Mailer.sendTextToCarrier("Your two factor authentication code is: " + tfaCode,"Faulker Hospital",ed.getPhoneNumber(),carrier);
        return tfaCode;
    }

//    public int runEmailTFA(EmployeeData ed, String ){
//        int tfaCode = generateCode();
//        Mailer.sendMail("Your two factor authentication code is: " + tfaCode,"Faulker Hospital",);
//    }
}

