package com.sanjaya.badsymptoms;

import android.app.Activity;
import android.app.Application;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sanjaya.badsymptoms.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat;
import com.sanjaya.badsymptoms.activitylifecyclecallbackscompat.ApplicationHelper;

import java.util.ArrayList;

public class BadSymptoms {
    private boolean LOG_PRINT = true;
    private final String LOG_TAG = BadSymptoms.class.getSimpleName();
    public GestureDetector gestureDetector;
    private String application,activityName;
    private Activity activity;
    public GestureListener gestureListener;
    private Application app;
    private ArrayList<View> allView;


    public BadSymptoms(Context context){
        activity = (Activity) context;
        application = getApplicationName(context);
        activityName = this.activity.getClass().getSimpleName();

        gestureListener = new GestureListener(application,activityName);
        gestureDetector = new GestureDetector(context, gestureListener);

        //allView = getAllChildren(activity.getWindow().getDecorView().getRootView());
        allView = getAllChildren(activity.getWindow().getDecorView().findViewById(android.R.id.content));
        logView();

        if (activity instanceof ListActivity) {
            ListActivity listActivity = (ListActivity) activity;
            ListView lv = listActivity.getListView();
            onTouch(lv);
        }

        if (activity instanceof ExpandableListActivity) {
            ExpandableListActivity expandableListActivity = (ExpandableListActivity) activity;
            ListView lv = expandableListActivity.getExpandableListView();
            onTouch(lv);
        }

        app = activity.getApplication();
        ApplicationHelper.registerActivityLifecycleCallbacks(app, new ActivityLifecycleCallbacksCompat() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivityCreated " + activity.getClass());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivityStarted " + activity.getClass());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivityResumed " + activity.getClass());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogSave.printLog(application);
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivityPaused " + activity.getClass());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivityStopped " + activity.getClass());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivitySaveInstanceState " + activity.getClass());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (LOG_PRINT)
                    Log.i(LOG_TAG, "onActivityDestroyed " + activity.getClass());
            }
        });
    }

    private void logView() {
        for (View child : allView) {
            if (child instanceof View) {
                onTouch(child);
            }
            if (LOG_PRINT)
                Log.i(activityName,"View Name: "+getViewName(child));
        }
    }

    private static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }
    public GestureDetector getGestureDetector(){
        return this.gestureDetector;
    }

    public void setGestureDetector(GestureDetector gestureDetector){
        this.gestureDetector = gestureDetector;
    }

    public void resumeActivity() {
        //jSonSave("user", application, activityName, "", "login");
    }

     public void onTouch(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureListener.setView(getViewName(view));
                gestureDetector.onTouchEvent(motionEvent);

                return false;
            }
        });
    }

    public String getViewName(View view) {
        String result;

        try {
            result = view.getResources().getResourceEntryName(view.getId());
        } catch (Exception e) {
            result = String.valueOf(view.getId());
        }

//        if (LOG_PRINT)
//            Log.i(LOG_TAG,"view Name: "+ result);

        return result;
    }

    public void saveMenu(String jenis,String menu){
        LogSave.jSonSave("user", application, activityName, jenis + " : " + menu, "Touch");
        //LogSave.printLog(application);
    }

    public ArrayList<View> getAllChildren(View v) {
        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();

            if (!(v.getId() < 0 )) {
                viewArrayList.add(v);
            }
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();

            if (!(v.getId() < 0 )) {
                viewArrayList.add(v);
            }

            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }
}
