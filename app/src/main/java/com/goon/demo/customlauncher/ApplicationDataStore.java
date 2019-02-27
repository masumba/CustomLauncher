package com.goon.demo.customlauncher;

public class ApplicationDataStore {

    private static ApplicationDataStore _selfInstance = null;

    private String[] allowedLauncherApplicationList = new String[]{"com.demo.goon.androidwizard"};
    private boolean allowOverDrag;
    private int clickCountStart = 0;
    private int clickCountMax = 6;

    public ApplicationDataStore() {
    }

    public static ApplicationDataStore getInstance(){
        if (_selfInstance == null){
            _selfInstance = new ApplicationDataStore();
        }
        return _selfInstance;
    }

    public String[] getAllowedLauncherApplicationList() {
        return allowedLauncherApplicationList;
    }

    public void setAllowedLauncherApplicationList(String[] allowedLauncherApplicationList) {
        this.allowedLauncherApplicationList = allowedLauncherApplicationList;
    }

    public boolean isAllowOverDrag() {
        return allowOverDrag;
    }

    public void setAllowOverDrag(boolean allowOverDrag) {
        this.allowOverDrag = allowOverDrag;
    }

    public int getClickCountStart() {
        return clickCountStart;
    }

    public void setClickCountStart(int clickCountStart) {
        this.clickCountStart = clickCountStart;
    }

    public int getClickCountMax() {
        return clickCountMax;
    }

    public void setClickCountMax(int clickCountMax) {
        this.clickCountMax = clickCountMax;
    }
}
