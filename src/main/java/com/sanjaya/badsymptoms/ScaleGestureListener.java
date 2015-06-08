package com.sanjaya.badsymptoms;

import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.DragEvent;

public class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private boolean LOG_PRINT = true;
    private final String LOG_TAG = ScaleGestureListener.class.getSimpleName();

    private float lastSpanX;
    private float lastSpanY;

    String application,activity,view;

    public  ScaleGestureListener (String application, String activity){
        this.application = application;
        this.activity = activity;
    }

    public void setView(String view){
        this.view = view;
    }

    private void jSonActivity(String msg) {
        LogSave.jSonSave("user",this.application,this.activity,this.view,msg);
        //LogSave.printLog(this.application);
        logPrint("Activity: " + this.activity + ", view :" + this.view + ", Gesture: " + msg);
    }

    private void logPrint(String msg){
        if (LOG_PRINT)
            Log.i(LOG_TAG, msg);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        lastSpanX = detector.getCurrentSpanX();
        lastSpanY = detector.getCurrentSpanY();

        logPrint("before scale -> X: " + lastSpanX + ", Y: " + lastSpanY);

        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //jSonActivity("Scale");

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        jSonActivity("Scale");
        lastSpanX = detector.getCurrentSpanX();
        lastSpanY = detector.getCurrentSpanY();

        logPrint("after scale --> X: " + lastSpanX + ", Y: " + lastSpanY);
    }
}
