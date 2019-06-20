package com.example.wechattab.utils;

import android.util.Log;

import com.example.wechattab.BuildConfig;

public class LogUtil {

    private static final String TAG = "hyman";
    private static boolean DebugTag = BuildConfig.DEBUG;

    public static void d(String msg, Object... args){
        if(DebugTag){
            Log.d(TAG, String.format(msg,args));
        }
    }
}
