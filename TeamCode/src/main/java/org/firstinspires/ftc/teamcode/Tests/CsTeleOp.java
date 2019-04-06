package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Hardware.CsHardware;

@TeleOp(name="Teleop", group="Cs")
//@Disabled

//THE KEY BELOW IS FOR USING IMAGE RECOGNITION -PULKIT
//AT5CUvf/////AAAAGaBn6TlejU79iRr5dpGz0Msa4+WbMquS0c0rHQGMURBOGIxPznOmaavjYRYfWHE/qRnpaHDvKIVV1drOmZguwKjiTVfUzVpkRgxdFzcVDsNBldxzhrcSl+bRKGlNv3zKHDfaOJioTa7uzIN/uKUzdJPX+o5PQQxRPYXBuIvAkASbZ9/MVjq5u3Jltyw3Gz9DCPVgxqcMKILOwv9FpMDMRTcgeRwk7f+pPd8f5FmB8ehr3xiDbPxydmYAkpuqQ6Mx2qiggbSlzl4uTm2JeqOP3hbej+ozcevtHKh9C4S3eKodfDUpKekBfdOuR2aer0FwrWxfAqmdOewy5Tei71lLAOgEzx+vo6OPKpSzbTh1gFzI

public class CsTeleOp extends OpMode{

    CsHardware robot  = new CsHardware();

    // Create variables for motor power
    private double lPower = 0; //left wheel
    private double rPower = 0; //right wheel
    private double fPower = 0; //front power
    private double bPower = 0; //back power
    private double hPower = 0; //hook power
    private double aPowerExt = 0; //arm extension power
    private double aPowerTIlt = 0; //intake bocx tilt power
    private double boxLiftPower = 0; // lifty boi power


    private double boxPower = 0; //boxPower
    private double boxTilt = 0;


    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        //robot.sensorColor.enableLed(false)
    }

    @Override
    public void loop() {

        if (gamepad1.right_stick_y != 0)
        {
            lPower = gamepad1.right_stick_y;
            rPower = gamepad1.right_stick_y;
        }

        else if (gamepad2.right_stick_y != 0)
        {
            lPower = gamepad2.right_stick_y;
            rPower = gamepad2.right_stick_y;
        }
        else
        {
            lPower = 0;
            rPower = 0;
        }
        if (gamepad1.right_stick_x != 0)
        {
            fPower = -gamepad1.right_stick_x;
            bPower = -gamepad1.right_stick_x;
        }
        else if (gamepad2.right_stick_x != 0)
        {
            fPower = -gamepad2.right_stick_x;
            bPower = -gamepad2.right_stick_x;
        }
        else
        {
            fPower = 0;
            bPower = 0;
        }

        //rotate
        if(gamepad1.left_stick_x > 0 || gamepad2.left_stick_x > 0)
        {
            lPower = -1; //lpower used to be 1
            rPower = 1; //rpower used to be -1
            fPower = -1;
            bPower = 1;
        }
        else if(gamepad1.left_stick_x < 0 || gamepad2.left_stick_x < 0)
        {
            lPower = 1; //lpower used to be -1
            rPower = -1; //used to be 1
            fPower = 1;
            bPower = -1;
        }

        if(rPower > 1)
        {
            rPower = 1;
        }
        else if (rPower < -1)
        {
            rPower = -1;
        }

        if(fPower > 1)
        {
            fPower = 1;
        }
        else if (fPower < -1)
        {
            fPower = -1;
        }

        if(bPower > 1)
        {
            bPower = 1;
        }
        else if (bPower < -1)
        {
            bPower = -1;
        }



//      SETTING POWERS AND POSITIONS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        robot.motorLeft.setPower(fPower);
        robot.motorRight.setPower(bPower);
        robot.motorFront.setPower(rPower);
        robot.motorBack.setPower(lPower);


//      TELEMETRY
        telemetry.addData("Left Motor Power", gamepad1.left_stick_y);
        telemetry.addData("Right Motor Power", gamepad1.left_stick_y);
        telemetry.addData("Front Motor Power", gamepad1.left_stick_x);
        telemetry.addData("Back Motor Power", gamepad1.left_stick_x);
        telemetry.addData("Hook Motor Power", gamepad2.left_stick_y);

    }

//--------------------------------- FUNCTIONS ----------------------------------------------------

    public double scalePower(double power1)
    {
        double power2;
        if(power1 > 0){
            power2 = Math.pow(Math.abs(power1), 0.6);
        } else if(power1 < 0){
            power2 = -Math.pow(Math.abs(power1), 0.6);
        } else{
            power2 = 0;
        }

        return power2;
    }
}