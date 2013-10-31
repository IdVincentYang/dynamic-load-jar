package com.example.dynamic_load_sample;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import dalvik.system.DexClassLoader;

public class MyLoader {
	
	private static Context mContext = null;
	private static String mDexPath;
	private static String mOptimizedDexPath;
	@SuppressWarnings("rawtypes")
	private static WeakReference mApkRef = null;
	
	@SuppressWarnings("rawtypes")
	public static void setApplication(App app) {
		mContext = app;
		mDexPath =
				app.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();
		mOptimizedDexPath = 
				app.getDir("optimized_dex", Context.MODE_PRIVATE).getAbsolutePath();
        try {
        	String packageName = app.getPackageName();
            Object currentActivityThread = RefInvoke.invokeStaticMethod(
                    "android.app.ActivityThread", "currentActivityThread",
                    new Class[] {}, new Object[] {});
            HashMap mPackages = (HashMap) RefInvoke.getFieldOjbect(
                    "android.app.ActivityThread", currentActivityThread,
                    "mPackages");
            mApkRef = (WeakReference) mPackages.get(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void loadLibrary(String name) throws IOException {

		String path = getLibraryPath(name);
		ClassLoader originClassLoader = (ClassLoader) RefInvoke.getFieldOjbect(
				"android.app.LoadedApk",
				mApkRef.get(),
				"mClassLoader");
		DexClassLoader dLoader = new DexClassLoader(
				path,
				mOptimizedDexPath,
				null,
				originClassLoader
				);
		RefInvoke.setFieldOjbect("android.app.LoadedApk", "mClassLoader",
				mApkRef.get(), dLoader);
	}
	
	private static String getLibraryPath(String name) throws IOException {

		File file = new File(mDexPath + "/" + name);
		do
		{
			if (file.exists()) break;
			
			AssetManager manager = mContext.getAssets();
			InputStream is = manager.open(name);
			OutputStream dexWriter = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buf = new byte[8192];
			int len;
			while((len = is.read(buf, 0, buf.length)) > 0) {
				dexWriter.write(buf, 0, len);
			}
			dexWriter.close();
			is.close();
		} while(false);
		return file.getAbsolutePath();
	}
}
