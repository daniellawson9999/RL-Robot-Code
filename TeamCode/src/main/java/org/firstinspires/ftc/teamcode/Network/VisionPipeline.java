package org.firstinspires.ftc.teamcode.Network;

import com.disnodeteam.dogecv.OpenCVPipeline;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class VisionPipeline extends OpenCVPipeline {
    public Mat workingMat = new Mat();
    public boolean imagedUpdated = false;
    public Model model;
    @Override
    public Mat processFrame(Mat rgba, Mat gray) {
        rgba.copyTo(workingMat);
        Imgproc.cvtColor(workingMat,workingMat,Imgproc.COLOR_RGBA2RGB);
        imagedUpdated = true;
        return rgba;
    }
    public void initModel(String modelName, Action[] actions){
        model = new Model(modelName,actions);
    }
    public Action predictAction(){
        return model.predict(workingMat);
    }
    public enum Action{
        Left,Right,Forwards,Backwards,CW,CCW
    }

    public class Model {

        public Action[] actions;

        public Interpreter model;

        //add model stuff
        public Model(String modelName, Action[] actions){


            this.actions = actions;


            String baseDirectory = "/sdcard/FIRST/CS/";
            String path = baseDirectory + modelName + ".tflite";
            File f = new File(path);
            model = new Interpreter(f);

        }
    /*
  where are the file on the phone? and how to upload. Credit to team 9773 for uploading examples.
        storage / emulated / FIRST / team9773 / json18
        1) open terminal tab on android studio
        2) get to the right dir on the computer, for example
        cd TeamCode/src/main/java/org/firstinspires/ftc/teamcode/json/
        3) push a file to the phone:
                              /sdcard/FIRST/CS/
        adb push cnnrandomL.tflite /sdcard/FIRST/CS/
        adb push myfile.json /sdcard/FIRST/team9773/json18/
        location of adb on mac: $HOME/Library/Android/sdk/platform-tools
          where you can get the $HOME value by typing "echo $HOME" in a terminal
          export PATH=$PATH:$HOME/Library/Android/sdk/platform-tools
        4) get a file from the phone
        adb pull  /sdcard/FIRST/team9773/json18/myfile.json
*/
        public Action predict(Mat mat){
            int numActions = actions.length;
            double[] values = new double[numActions];
            for (int i = 0; i < numActions; i++){
                double[] actionInput = new double[numActions];
                actionInput[i] = 1;
                int width = mat.width();
                int height = mat.height();
                double[][][] imageArray = new double[height][width][3];
                for (int k = 0; k< height; k++){
                    for (int j = 0; j < width; j++){
                        double[] element = mat.get(k,j);
                        for (int l = 0; l < element.length; l++) imageArray[k][j][l] = element[l] / 255;
                    }
                }
                Object[] inputs = {imageArray,actionInput};
                double[] output = new double[1];
                Map<Integer,Object> outputs = new HashMap();
                outputs.put(0,output);
                model.runForMultipleInputsOutputs(inputs,outputs);
                values[i] = ((double) outputs.get(0));
            }
            int action = -1;
            double value = 0;
            for (int i = 0; i < numActions; i++){
                if(action == -1 || values[i] > value){
                    action = i;
                    value = values[i];
                }
            }
            Action actionName = actions[action];
            return actionName;
        }
        //k-means clustering will go here
        public Mat processFrame(Mat frame){
            return frame;
        }

    }

}
