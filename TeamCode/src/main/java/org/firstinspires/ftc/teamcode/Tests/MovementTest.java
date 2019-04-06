package org.firstinspires.ftc.teamcode.Tests;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;

@Autonomous(name="MovementTest", group="Cs")
public class MovementTest extends CsDriving {
    public void runOpMode()
    {
        super.runOpMode();
        while(opModeIsActive()){
            telemetry.addData("Turning degrees: ", 30);
            telemetry.update();
            relativeGyroDrive(30,xyz,1,3000);
            sleep(500);

            telemetry.addData("Turning degrees: ", -30);
            telemetry.update();
            relativeGyroDrive(-30,xyz,1,3000);
            sleep(500);

            telemetry.addData("Turning degrees: ", 45);
            telemetry.update();
            relativeGyroDrive(45,xyz,1,3000);
            sleep(500);

            telemetry.addData("moving inches right: ", 2);
            telemetry.update();
            latEncoderDrive(1,2,2,3000);
            sleep(500);

            telemetry.addData("moving inches forwards: ", 2);
            telemetry.update();
            encoderDrive(1,2,2,3000);
            sleep(500);

        }

    }
}
