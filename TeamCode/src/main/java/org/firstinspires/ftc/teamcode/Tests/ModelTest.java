package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.CsDriving;

import org.firstinspires.ftc.teamcode.Network.VisionPipeline.Action;
import org.opencv.core.Size;

@Autonomous(name="ModelTest", group="Cs")

public class ModelTest extends CsDriving {
    public void runOpMode()
    {
        super.usePipeline = true;
        super.modelName = "cnnrandomL";
        Action[] actions = {Action.Forwards,Action.Left,Action.Right};
        super.actions = actions;
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


        pipeline.disable();
    }
}
