package com.example.dynamic_load_sample;

import java.io.IOException;
import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App mInstance;
    
    public static App getInstance() {
    	return mInstance;
    }
    protected void attachBaseContext(Context base) {
    	super.attachBaseContext(base);  
    	MyLoader.setApplication(this);
    	try {
    		MyLoader.loadLibrary("dynamic-lib-dex.jar");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void onCreate() {
    	mInstance = this;
    	super.onCreate();
    }
}
