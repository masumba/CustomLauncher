package com.goon.demo.customlauncher;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class NavigationDrawerLock {

    private static NavigationDrawerLock _selfInstance = null;

    public NavigationDrawerLock(){}

    public static NavigationDrawerLock getInstance(){
        if (_selfInstance == null){
            _selfInstance = new NavigationDrawerLock();
        }
        return _selfInstance;
    }

    public static void enableStatusBarExpansion(Context context){
        WindowManager manager = ((WindowManager)context.getApplicationContext()
        .getSystemService(Context.WINDOW_SERVICE));

        Activity activity = (Activity)context;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

    }

}
