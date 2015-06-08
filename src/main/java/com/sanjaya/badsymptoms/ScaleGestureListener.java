package com.sanjaya.badsymptoms;

import android.view.ScaleGestureDetector;
import android.view.DragEvent;

public class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
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
