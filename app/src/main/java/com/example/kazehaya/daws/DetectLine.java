package com.example.kazehaya.daws; /**
 * Created by Kazehaya on 1/21/2015.
 */
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.putText;

public class DetectLine  extends MainCameraView{
    private static final String TAG = "Detect Line Method";

    public static void getLine(Mat rgb,Mat gray) {

        double m,c;

        Mat guassians = new Mat();
        Mat contours = new Mat();
        Mat lines = new Mat();
        Rect roi = new Rect(0,200,640,200);
        List<Lines> filteredLines = new ArrayList<>();
        Point start,end;
        Point PText =new Point() ;
        PText.x=60 ;PText.y=80 ;
        Point PText2 =new Point() ;
        PText2.x=60 ;PText2.y=100 ;

        double[] vect;
        double x1,y1,x2,y2,angle;

        Mat mRgbRoi = new Mat(gray,roi); // Set ROI

        Imgproc.GaussianBlur(mRgbRoi,guassians,new Size(5,5),0,0); // do guassian blur
        Imgproc.Canny(guassians,contours,Constants.LOW_THRESHOLD,Constants.HIGH_THRESHOLD); // apply Canny's Edge Detection
        Imgproc.HoughLinesP(contours,lines,Constants.RHO,Constants.THETA,Constants.HOUGH_THRESHOLD,Constants.LINE_SIZE,Constants.LINE_GAP);

        for (int i = 0 ; i < lines.cols() ; i++) {

            vect = lines.get(0,i);

            x1 = vect[0];
            y1 = vect[1] + Constants.ROI_HEIGHT;
            x2 = vect[2];
            y2 = vect[3] + Constants.ROI_HEIGHT;

            angle = Math.toDegrees(Math.atan((y2-y1)/(x2-x1)));

            if(Math.abs(angle) > 30) {
                filteredLines.add(new Lines(x1,y1,x2,y2));
            }
        }

        Log.i(TAG,"Filtered Line : " + filteredLines.size());

        for (int i = 0 ; i < filteredLines.size() ; i++) {
            start = filteredLines.get(i).getStart();
            end = filteredLines.get(i).getEnd();

            /// calculate for line  y=mx+c
            m=(end.y - start.y)/(end.x - start.x);
            c=start.y-(m*start.x);

            Point PointTop=new Point();
            PointTop.x=(200-c)/m ;
            PointTop.y=200;

            Point PointBot=new Point();
            PointBot.x=(640-c)/m ;
            PointBot.y=640;

            //Log.i(TAG,"Start (" + start.x + "," + start.y + ") End (" + end.x + "," + end.y + ")");

            if( start.x < 245 || end.x < 245 || start.x > 390  || end.x > 390) {
//                Core.line(rgb,  PointBot, PointTop, Constants.BLUE, Constants.LINE_THICK);
                    Core.line(rgb,  start, end, Constants.BLUE, Constants.LINE_THICK);
                        putText(rgb,"InLane! ",PText,1,2,Constants.WHITE,2);

              //MainCameraView.txstatus.setText("Normal");
            }
            else if (((end.y - start.y) > 15)  && CkCar==1  ) {
                Core.line(rgb,  start, end, Constants.ORG, Constants.LINE_THICK);
                putText(rgb,"Overtaking!! ",PText2,1,2,Constants.WHITE,2);
            }

            else if ( (end.y - start.y) > 15){ // Lane Departure // beep if Line.length > 15
                ToneGenerator sound = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 200);
                sound.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP,200);
                Core.line(rgb,  PointBot, PointTop, Constants.RED, Constants.LINE_THICK);
                putText(rgb,"LaneChanging!! ",PText2,1,2,Constants.WHITE,2);
               // Core.line(rgb, filteredLines.get(i).getStart(), filteredLines.get(i).getEnd(), Constants.RED, Constants.LINE_THICK);
                //putText(rgb,"laneCh!!! ",end,1,2,Constants.WHITE,2);
                //MainCameraView.txstatus.setVisibility(View.INVISIBLE);
                System.gc();   //  Call garbage collector for free memory
            }

        }

    }

}
