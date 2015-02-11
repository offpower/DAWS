package com.example.kazehaya.daws;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import android.app.Activity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.opencv.photo.Photo.fastNlMeansDenoising;


public class MainCameraView extends Activity implements CvCameraViewListener2 {

    private static final String TAG = "Main Camera Activity";

    private CameraBridgeViewBase mOpenCvCameraView;

    private TextView lineNum ;

    private Mat mRgb,mGray,rgb;

    private List<Lines> lines;

    public static CascadeClassifier carDetector;
    private File mCascadeFile;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization

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

                        //cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main_camera_view);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.java_camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        lineNum = ((TextView) findViewById(R.id.line_number));

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_camera_view, menu);
        return true;

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgb = new Mat();
        mGray = new Mat();
        rgb = new Mat();
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        rgb = inputFrame.rgba();
        mRgb = inputFrame.rgba();
        mGray = inputFrame.gray();
        DetectLine.getLine(mRgb,mGray);
        DetectVehicle.getCar(mRgb,rgb);
        return mRgb;
    }

}
