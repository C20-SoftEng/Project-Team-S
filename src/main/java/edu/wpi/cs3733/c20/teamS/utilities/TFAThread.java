package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.twoFactor.TwoFactorAuthenticator;

public class TFAThread extends Thread {

    public TwoFactorAuthenticator tfa;

    public TFAThread(TwoFactorAuthenticator tfa){
        this.tfa = tfa;
    }

    @Override
    public void run(){
        //tfa = new TwoFactorAuthenticator();
        tfa.code = tfa.runTextTFA(tfa.ed,tfa.carrier,tfa.code);
        System.out.println(tfa.code);

    }



}
