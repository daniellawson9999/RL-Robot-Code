package org.firstinspires.ftc.teamcode.Autonomous;
import org.firstinspires.ftc.teamcode.Network.VisionPipeline;
import org.firstinspires.ftc.teamcode.Network.VisionPipeline.Action;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

@Autonomous(name="CsRandom", group="Cs")

public class CsRandom extends CsDriving {
    int forwardDistance  = 3;
    int turnAngle = 5;
    int timeout = 5000;
    public void runOpMode()
    {
        super.usePipeline = true;
        super.modelName = "cnnrandomP1";
        Action[] actions = {Action.Forwards,Action.CW,Action.CCW};
        super.actions = actions;
        super.runOpMode();

        while(opModeIsActive()) {
            if (pipeline.imagedUpdated){
                //telemetry.addData("size: ",image.size());
                //telemetry.update();
                Action action= pipeline.predictAction();
                telemetry.addData("Taking action: ",action);
                if(action == Action.Forwards) {
                    encoderDrive(1,forwardDistance,forwardDistance,timeout);
                }else if (action == Action.CW) {
                    relativeGyroDrive(-turnAngle,"z",1,timeout);
                }else if (action ==  Action.CCW) {
                    relativeGyroDrive(turnAngle,"z",1,timeout);

                }else {
                    telemetry.addLine("Action not found");
                    telemetry.update();
                }
                pipeline.imagedUpdated = false;
            }
        }

        pipeline.disable();

    }
}
