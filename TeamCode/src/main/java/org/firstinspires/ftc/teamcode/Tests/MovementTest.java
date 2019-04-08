package org.firstinspires.ftc.teamcode.Tests;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;

@Autonomous(name="MovementTest", group="Cs")
public class MovementTest extends CsDriving {
    public void runOpMode()
    {
        super.runOpMode();
        while(opModeIsActive()){
            telemetry.addData("Turning degrees: ", 5);
            telemetry.update();
            relativeGyroDrive(5,xyz,1,3000);
            relativeGyroDrive(5,xyz,1,3000);
            sleep(500);

            telemetry.addData("Turning degrees: ", -5);
            telemetry.update();
            relativeGyroDrive(-5,xyz,1,3000);
            relativeGyroDrive(-5,xyz,1,3000);
            sleep(500);


        }

    }
}
