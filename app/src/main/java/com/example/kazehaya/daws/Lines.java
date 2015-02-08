package com.example.kazehaya.daws;

import org.opencv.core.Point;

/**
 * Created by Kazehaya on 2/9/2015.
 */
public class Lines {
    private Point start;
    private Point end;
    public double angle;

    public Lines(double x1, double y1, double x2, double y2) {

        setStart(new Point(x1,y1));
        setEnd(new Point(x2,y2));
        angle = Math.toDegrees(Math.atan((y2-y1)/(x2-x2)));

    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
