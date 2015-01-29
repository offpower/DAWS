package com.example.kazehaya.daws; /**
 * Created by Kazehaya on 1/21/2015.
 */
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class DetectLine {
    private static final String TAG = "Detect Line Method : ";

    public static void getLine(Mat rgb,Mat gray) {

        Mat guassians = new Mat();
        Mat contours = new Mat();
        Mat lines = new Mat();
        Rect roi = new Rect(0,240,640,240);

        Mat mRgbRoi = new Mat(gray,roi); // Set ROI

        Imgproc.GaussianBlur(mRgbRoi,guassians,new Size(5,5),0,0); // do guassian blur
        Imgproc.Canny(guassians,contours,Constants.LOW_THRESHOLD,Constants.HIGH_THRESHOLD); // apply Canny's Edge Detection
        Imgproc.HoughLinesP(contours,lines,Constants.RHO,Constants.THETA,Constants.HOUGH_THRESHOLD,Constants.LINE_SIZE,Constants.LINE_GAP);

        for (int i = 0 ; i < lines.cols() ; i++) {

            double[] vect = lines.get(0,i);

            double x1 = vect[0];
            double y1 = vect[1] + Constants.ROI_HEIGHT;
            double x2 = vect[2];
            double y2 = vect[3] + Constants.ROI_HEIGHT;

            Point start = new Point(x1,y1);
            Point end = new Point(x2,y2);

            Core.line(rgb,start,end,Constants.BLUE,Constants.LINE_THICK);
        }

    }
    
}
