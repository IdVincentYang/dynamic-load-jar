package com.example.dynamic_load_sample;

import android.app.Application;

public class App extends Application {
    private static App mInstance;
    
    public static App getInstance() {
    	return mInstance;
    }
    
    public void onCreate() {
    	mInstance = this;
    	super.onCreate();
    	MyLoader.getInstance().loadLibrary("dynamic-lib.jar");
    }
}
