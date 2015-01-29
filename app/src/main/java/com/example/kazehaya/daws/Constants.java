package com.example.kazehaya.daws;

import org.opencv.core.Scalar;

/**
 * Created by Kazehaya on 1/21/2015.
 */
public class Constants {
    public static Scalar WHITE = new Scalar(255,255,255);
    public static Scalar RED = new Scalar(255,0,0);
    public static Scalar BLUE = new Scalar(0,0,255);
    public static int LOW_THRESHOLD = 1;
    public static int HIGH_THRESHOLD = 100;
    public static int HOUGH_THRESHOLD = 50;
    public static int ROI_HEIGHT = 240;
    public static double RHO = 1;
    public static double THETA = Math.PI/180;
    public static double LINE_SIZE = 5;
    public static double LINE_GAP = 30;
    public static int LINE_THICK = 5;
}
