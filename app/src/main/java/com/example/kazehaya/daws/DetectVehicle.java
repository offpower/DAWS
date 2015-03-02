package com.example.kazehaya.daws;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Looper;
import android.util.Log;
import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static org.opencv.core.Core.putText;


/**
 * Created by UNKN0WN on 21-Jan-15.
 */
public  class DetectVehicle extends MainCameraView {

    public static void getCar(Mat rgb,Mat image){

        String TAG ="" ;
        Mat mGray=new Mat();
        Mat deNoiseGray=new Mat();
        MatOfRect cars = new MatOfRect();
        double scaleFactor = 1.2;
        int minNeighbors = 4;
        int flags = 0 ;
        Size minSize=new Size(90,70);
        Size maxSize=new Size(640,480);

        Imgproc.cvtColor(rgb, mGray, Imgproc.COLOR_BGRA2GRAY);

        // fastNlMeansDenoising(mGray,deNoiseGray);

        carDetector.detectMultiScale(mGray, cars, scaleFactor, minNeighbors, flags,  minSize,  maxSize);
        Log.i(TAG, "detecting");

        Rect[] carsArray = cars.toArray();  //draw rectangle around detected object
        for (int j = 0; j <carsArray.length; j++) {
            Point Ptl= carsArray[j].tl() ;   //top left
            Point Pbr= carsArray[j].br() ;   //bottom right
            Point Ptr= new Point(Pbr.x-10,Ptl.y);
            ToneGenerator sound = new ToneGenerator(AudioManager.STREAM_ALARM, 200);

            Core.rectangle(image, carsArray[j].tl(), carsArray[j].br(),Constants.GREEN, Constants.LINE_THICK);
           // Log.i(TAG, "draw retangle");

            //if(carsArray[j].y > 240 && (carsArray[j].x > 213 && carsArray[j].x <426 ) ) {
            if(Ptl.x > 213 && Pbr.x < 426  ) {
                sound.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                Core.rectangle(image, carsArray[j].tl(), carsArray[j].br(),Constants.RED, Constants.LINE_THICK);
                putText(image,"Warning !!! ",Ptl,1,2,Constants.WHITE,2);

                System.gc();  //  Call garbage collector for free memory
            }
        }

    }

}

