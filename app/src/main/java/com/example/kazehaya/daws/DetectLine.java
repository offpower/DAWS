package com.example.kazehaya.daws; /**
 * Created by Kazehaya on 1/21/2015.
 */
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class DetectLine {
    public static Mat getLine(Mat rgb) {

        Mat contours = new Mat();
        Mat mGray = new Mat();
        Mat lines = new Mat();

        Imgproc.cvtColor(rgb,mGray,Imgproc.COLOR_BGRA2GRAY); // convert RGB image to Grayscale image
        Imgproc.Canny(mGray,contours,Constants.LOW_THRESHOLD,Constants.HIGH_THRESHOLD);
        Imgproc.HoughLinesP(contours,lines,Constants.RHO,Constants.THETA,Constants.HIGH_THRESHOLD,Constants.LINE_SIZE,Constants.LINE_GAP);

        for (int i = 0 ; i < lines.cols() ; i++) {
            double[] vect = lines.get(0,i);
            Point start = new Point(vect[0],vect[1]);
            Point end = new Point(vect[2],vect[3]);
            Core.line(rgb,start,end,Constants.WHITE,Constants.LINE_THICK);
        }
        return rgb;
    }
}
