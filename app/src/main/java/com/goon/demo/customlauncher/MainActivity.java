package com.goon.demo.customlauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends FragmentActivity {

    int clickCount = ApplicationDataStore.getInstance().getClickCountStart();
    int clickMax = ApplicationDataStore.getInstance().getClickCountMax();
    int unlockClick = 0;
    boolean drawerAccess;

    TextView tvShowDrawer;
    Button btnDeviceUnlock,btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerAccess = ApplicationDataStore.getInstance().isAllowOverDrag();

        btnDeviceUnlock = (Button)findViewById(R.id.btn_deviceUnlock);
        btnDeviceUnlock.setTextColor(getResources().getColor(android.R.color.transparent));

        btnSetting = (Button)findViewById(R.id.btn_settings);
        btnSetting.setTextColor(getResources().getColor(android.R.color.transparent));

        btnSetting.setEnabled(false);

        drawerAccessState();

    }

    public void drawerAccessState() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                myIntent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(myIntent, 101);
                /**/

            } else {

                if (!drawerAccess) {
                    preventStatusBarExpansion(this, drawerAccess);
                    //tvShowDrawer.setBackgroundColor(Color.TRANSPARENT);
                } else if (drawerAccess) {

                    //

                    tvShowDrawer.setBackgroundColor(Color.CYAN);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    public void lockNavDragBar(View view) {

        if (clickCount == clickMax) {

            if (drawerAccess) {
                ApplicationDataStore.getInstance().setAllowOverDrag(false);
                clickCount = ApplicationDataStore.getInstance().getClickCountStart();
            } else if (!drawerAccess) {
                ApplicationDataStore.getInstance().setAllowOverDrag(true);
                clickCount = ApplicationDataStore.getInstance().getClickCountStart() + 4;
            }
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            clickCount++;
        }
    }

    /*Disable*/
    public static void preventStatusBarExpansion(Context context, boolean drawerAccess) {
        WindowManager manager = ((WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        Activity activity = (Activity) context;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = 0;
        if (resId > 0) {
            result = activity.getResources().getDimensionPixelSize(resId);
        }

        layoutParams.height = result;
        layoutParams.format = PixelFormat.TRANSPARENT;

        customViewGroup viewGroup = new customViewGroup(context);

        manager.addView(viewGroup, layoutParams);

    }

    public void unlockDevice(View view) {
        unlockClick++;
        if (unlockClick == 5){
            unlockClick = 0;
            btnSetting.setEnabled(true);
        } else {
            btnSetting.setEnabled(false);
        }
    }

    public void openSettings(View view) {
        openDeviceSettingsPanel();
        btnSetting.setEnabled(false);

    }

    public static class customViewGroup extends ViewGroup {

        public customViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
            //
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {

            Log.v("customViewGroup", "*********Intercepted");
            return true;
        }
    }
    /**/

    /*enable*/
    public void openDeviceSettingsPanel() {

        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);

        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

    }
    /**/
}
