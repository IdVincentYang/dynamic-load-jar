package com.example.dynamic_load_sample;

import java.io.IOException;

import android.app.Application;

public class App extends Application {
    private static App mInstance;
    
    public static App getInstance() {
    	return mInstance;
    }
    
    public void onCreate() {
    	mInstance = this;
    	super.onCreate();
		try {
			MyLoader.getInstance().loadLibrary("dynamic-lib-dex.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
