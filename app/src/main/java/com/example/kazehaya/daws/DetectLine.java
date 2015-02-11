package com.example.kazehaya.daws; /**
 * Created by Kazehaya on 1/21/2015.
 */
import android.util.Log;
import android.widget.TextView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class DetectLine {
    private static final String TAG = "Detect Line Method";

    public static void getLine(Mat rgb,Mat gray) {

        Mat guassians = new Mat();
        Mat contours = new Mat();
        Mat lines = new Mat();
        Rect roi = new Rect(0,240,640,240);
        List<Lines> filteredLines = new ArrayList<>();
        Point start,end;

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
            //Log.i(TAG,"Start (" + start.x + "," + start.y + ") End (" + end.x + "," + end.y + ")");
            Core.line(rgb, filteredLines.get(i).getStart(), filteredLines.get(i).getEnd(), Constants.BLUE, Constants.LINE_THICK);
        }

    }

    public static void DetectLane(ArrayList<Lines> lines) {
        int length = lines.size();
        Point start,end;
        for (Lines line : lines) {
            start = line.getStart();
            end = line.getEnd();
            if( start.x < 210 || end.x < 210 || start.x > 430 || end.x > 430) {
                // Do Something

            }
        }

    }
}
