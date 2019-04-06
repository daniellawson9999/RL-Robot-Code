package org.firstinspires.ftc.teamcode.Tests;
import org.firstinspires.ftc.teamcode.Network.VisionPipeline;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

@Autonomous(name="OpencvTest", group="Cs")

public class OpencvTest extends CsDriving {
    public void runOpMode()
    {
        super.usePipeline = true;
        super.runOpMode();
        while(opModeIsActive()){
            Size size;
            try{
                size = pipeline.workingMat.size();
            }catch(NullPointerException e){
                size = new Size();
            }
            telemetry.addData("Size: ", size);
            telemetry.update();
        }

    }
}
