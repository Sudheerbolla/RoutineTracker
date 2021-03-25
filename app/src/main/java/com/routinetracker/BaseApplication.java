package com.routinetracker;

import android.app.Application;

import com.routinetracker.utils.AppStorage;
import com.routinetracker.utils.StaticUtils;

public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    public static final String NIGHT_MODE = "NIGHT_MODE";
    public AppStorage appStorage;
    public static int filterSortingMode = 0;
    public static boolean isOtgAvailable = false;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        setUpApplication();
    }

    private void setUpApplication() {
        StaticUtils.setUpDeviceDefaultDetails(this);
        appStorage = AppStorage.getInstance(this);
        try {
//            new DBHelper(this).cleanTransferModelRecordsPeriodically();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized BaseApplication getInstance() {
        if (baseApplication == null) baseApplication = new BaseApplication();
        return baseApplication;
    }

}
