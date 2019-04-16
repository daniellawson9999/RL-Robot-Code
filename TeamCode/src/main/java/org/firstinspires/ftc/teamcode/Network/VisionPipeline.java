package org.firstinspires.ftc.teamcode.Network;

import com.disnodeteam.dogecv.OpenCVPipeline;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class VisionPipeline extends OpenCVPipeline {
    public Telemetry telemetry;
	//Matrix to copy the rgba image matrix supplied by the the OpenCVPipeline
    public Mat workingMat = new Mat();
    //Keeps track if the image has been updated since the end of the last action
    public boolean imagedUpdated = false;
    //field to hold the Model class
    public Model model;
    @Override
    public Mat processFrame(Mat rgba, Mat gray) {
        rgba.copyTo(workingMat);
        Imgproc.cvtColor(workingMat,workingMat,Imgproc.COLOR_RGBA2RGB);
        //telemetry.addData("chanels: ",rgba.channels());
        imagedUpdated = true;
        return rgba;
    }
    //initialize the model
    public void initModel(String modelName, Action[] actions){
        model = new Model(modelName,actions);
    }
    //Copy working mat into another matrix before passing it to model.predict to prevent overwriting (may be unnessary)
    public Action predictAction(){
        Mat copy = new Mat();
        //Mat save = new Mat();


        workingMat.copyTo(copy);
        Core.rotate(copy,copy,Core.ROTATE_90_COUNTERCLOCKWISE);
        //write to a file for testing

        //copy.copyTo(save);
        //save.convertTo(save,Imgproc.COLOR_RGBA2BGR);
        //Imgproc.cvtColor(save,save,Imgproc.COLOR_RGBA2BGR);
        //Imgcodecs.imwrite("/sdcard/FIRST/Photos/original.png",save);

        Imgproc.resize(copy,copy,new Size(copy.width()/16,copy.height()/16));

        //copy.copyTo(save);
        //Imgproc.cvtColor(save,save,Imgproc.COLOR_RGBA2BGR);
        //Imgcodecs.imwrite("/sdcard/FIRST/Photos/resized.png",save);

        copy.convertTo(copy,CvType.CV_32F);

        return model.predict(copy);
    }
    //The bug is probably right here, if the first Action.Left as an int value of 1 this may be a problem
    //But it shouldn't be used this way
    public enum Action{
        Left,Right,Forwards,Backwards,CW,CCW
    }
    //Model object class which manages the tensorflow Interpreter 
    public class Model {
    	//Array of available actions (order matters)
        public Action[] actions;
        //The tensorflow interpreter
        public Interpreter model;

        //Model constructor
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
        //Returns the action with the greatest value
        public Action predict(Mat mat){
        	//convert to 64 bit float
            //store number of actions
            int numActions = actions.length;
            //create array to store the value of each action
            float[] values = new float[numActions];
            //get the value of each action
            //conver the mat to a a double array for tensorflow
            int width = mat.width();
            int height = mat.height();
            //3 dimensions for width,height, rgb, and an extra dimension for tensorflow
             float[][][][] imageArray = new float[1][height][width][3];
             for (int k = 0; k< height; k++){
                 for (int j = 0; j < width; j++){
                     //get the r,b,g array at index k,j, scale to 0-1, and store
                     double[] element = mat.get(k,j);
                     for (int l = 0; l < 3; l++){
                         imageArray[0][k][j][l] = (float) (element[l] / 255);
                    }
                 }
             }
            //telemetry.addData("imageArray height:",imageArray[0].length);
            //telemetry.addData("imageArray widdthh:",imageArray[0][0].length);
            //telemetry.addData("imageArray channels:",imageArray[0][0][0].length);
            //telemetry.update();
            for (int i = 0; i < numActions; i++){
            	//create a one-hot array for each action
                float[][] actionInput = new float[1][numActions];
                actionInput[0][i] = 1;

                //create object array to pass to tensorflow and to receive output
                Object[] inputs = {imageArray,actionInput};
                float[][] output = new float[1][1];
                Map<Integer,Object> outputs = new HashMap();
                outputs.put(0,output);
                //run the model!
                model.runForMultipleInputsOutputs(inputs,outputs);
                //store the value of the action
                values[i] = output[0][0];
                telemetry.addData("Action: ",actions[i]);
                telemetry.addData("Value: ", values[i]);
           }
            //find the index of the action w/ max value
            int action = 0;
            double value = values[0];
            for (int i = 0; i < numActions; i++){
                if(values[i] > value){
                    action = i;
                    value = values[i];
                }
            }
            telemetry.addData("max action: ", action);
            telemetry.addData("max value: ", value);
            //telemetry.update();
            //get and return the name of the action
            Action actionName = actions[action];
            return actionName;
        }
        //k-means clustering will go here
        public Mat processFrame(Mat frame){
            return frame;
        }

    }

}
