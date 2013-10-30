package com.example.dynamic_load_sample;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.StaticLayout;
import dalvik.system.DexClassLoader;

public class MyLoader {

	static private MyLoader mInstance = new MyLoader();
	
	static public MyLoader getInstance() {
		return mInstance;
	}

	private Context mContext;
	private String mDexPath;
	private String mOptimizedDexPath;

	private MyLoader() {
		mContext = App.getInstance();
		mDexPath = mContext.getDir(
				"dex", Context.MODE_PRIVATE
		).getAbsolutePath();
		mOptimizedDexPath = mContext.getDir(
				"optimized_dex", Context.MODE_PRIVATE
		).getAbsolutePath();
	}

	private void loadLibrary(String name) throws IOException {
		String path = getLibraryPath(name);
		DexClassLoader loader = new DexClassLoader(
				path,
				mOptimizedDexPath,
	    		null,
	    		mContext.getClassLoader()
        );
	}

	private String getLibraryPath(String name) throws IOException {
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
