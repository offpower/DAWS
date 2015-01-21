package com.example.kazehaya.daws;

import android.util.Log;
import org.opencv.core.Core;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


/**
 * Created by UNKN0WN on 21-Jan-15.
 */
public class DetectVehicle{

    public static Mat getCar(Mat rgb){
        String TAG ="" ;
        Mat mGray=new Mat();
        MatOfRect cars = new MatOfRect();
        double scaleFactor = 1.1;
        int minNeighbors = 0;
        int flags = 1 ;
        Size minSize=new Size(30,30);
        Size maxSize=new Size(200,200);

        Imgproc.cvtColor(rgb, mGray, Imgproc.COLOR_BGRA2GRAY);

        CascadeClassifier carCascade = new CascadeClassifier("CascadeCar.xml");

        try {

            carCascade.load("CascadeCar.xml");  //
            Log.i(TAG, " loading Cascade");
        }
        catch (Exception e) {
            Log.i(TAG, "error loading");
            Log.e("OpenCVActivity", "Error loading cascade", e);
        }

        carCascade.detectMultiScale(mGray, cars, scaleFactor, minNeighbors, flags,  minSize,  maxSize);
        Log.i(TAG, "detecting");
        Log.i(TAG, String.valueOf(cars));

        Rect[] carsArray = cars.toArray();  //draw rectangle around detected object
        for (int j = 0; j <carsArray.length; j++)
            Core.rectangle(rgb, carsArray[j].tl(), carsArray[j].br(), new Scalar(0, 255, 0, 255), 3);

    return rgb;
    }

}
