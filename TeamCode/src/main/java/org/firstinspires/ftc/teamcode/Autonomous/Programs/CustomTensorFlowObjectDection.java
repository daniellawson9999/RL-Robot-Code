package org.firstinspires.ftc.teamcode.Autonomous.Programs;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;
import org.firstinspires.ftc.teamcode.TensorFlow;
import org.firstinspires.ftc.teamcode.TensorFlow.RobotOrientation;

@Autonomous(name="CustomTensorFlowTest", group="Cs")
public class CustomTensorFlowObjectDection extends CsDriving {
    public void runOpMode()
    {
        super.runOpMode();
        TensorFlow tf = new TensorFlow(hardwareMap,TensorFlow.Device.Webcam,telemetry);
        tf.start();
        while(opModeIsActive()){
            telemetry.addData("value",tf.getMineralLocation(RobotOrientation.Left));
            telemetry.update();
        }
        tf.shutdown();

    }
}
