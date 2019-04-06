package org.firstinspires.ftc.teamcode.Network;

import com.disnodeteam.dogecv.OpenCVPipeline;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class VisionPipeline extends OpenCVPipeline {
    public Mat workingMat = new Mat();
    public boolean imagedUpdated = false;
    @Override
    public Mat processFrame(Mat rgba, Mat gray) {
        rgba.copyTo(workingMat);
        Imgproc.cvtColor(workingMat,workingMat,Imgproc.COLOR_RGBA2RGB);
        imagedUpdated = true;
        return rgba;
    }
}
