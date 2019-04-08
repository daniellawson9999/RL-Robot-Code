package org.firstinspires.ftc.teamcode.Tests;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;

@Autonomous(name="DriveTest", group="Cs")
public class DriveTest extends CsDriving {
    public void runOpMode()
    {
        super.runOpMode();
        while(opModeIsActive()){
            encoderDrive(1,3,3,5000);
        }

    }
}
