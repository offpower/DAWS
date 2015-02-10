package com.example.kazehaya.daws;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import org.opencv.core.Core;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;

/**
 * Created by UNKN0WN on 21-Jan-15.
 */
public  class DetectVehicle extends Activity {
    CascadeClassifier carDetector;
  /*  public void readXML() {
        String TAG ="" ;
        File mCascadeFile;

        try {
            // load cascade file from application resources
            InputStream is = getResources().openRawResource(R.raw.cars3);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            mCascadeFile = new File(cascadeDir, "cars3.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            carDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if (carDetector.empty()) {
                Log.e(TAG, "Failed to load cascade classifier");
                carDetector = null;
            } else
                Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

            //mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

            cascadeDir.delete();
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
        }
    } */

    public static Mat getCar(Mat rgb){

        String TAG ="" ;
        //String cascadeNameXML = "D:\\Work\\Senior_Project\\DAWS\\dataset\\cas1.xml";
        Mat mGray=new Mat();
        MatOfRect cars = new MatOfRect();
        double scaleFactor = 1.1;
        int minNeighbors = 0;
        int flags = 0 ;
        Size minSize=new Size(20,20);
        Size maxSize=new Size(200,200);

        Imgproc.cvtColor(rgb, mGray, Imgproc.COLOR_BGRA2GRAY);

       // CascadeClassifier carCascade = new CascadeClassifier(cascadeNameXML);



        //carDetector.detectMultiScale(mGray, cars, scaleFactor, minNeighbors, flags,  minSize,  maxSize);
        Log.i(TAG, "detecting");
        Log.i(TAG, String.valueOf(cars));

        Rect[] carsArray = cars.toArray();  //draw rectangle around detected object
        for (int j = 0; j <carsArray.length; j++) {
            Core.rectangle(rgb, carsArray[j].tl(), carsArray[j].br(), new Scalar(0, 255, 0, 255), 3);
            Log.i(TAG, "draw retangle");
        }


    return rgb;
    }

}

