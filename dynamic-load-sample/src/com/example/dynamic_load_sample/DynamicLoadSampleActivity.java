package com.example.dynamic_load_sample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class DynamicLoadSampleActivity extends Activity {

	private OnClickListener mStartStaticLibActivity = new OnClickListener() {
		
		public void onClick(View v) {
			Context ctx = v.getContext();
			ctx.startActivity(new Intent(ctx, com.example.static_lib.StaticLibActivity.class));
		}
	};
	
	private OnClickListener mStartDynamicLibActivity = new OnClickListener() {
		
		public void onClick(View v) {
			Context ctx = v.getContext();
			//ctx.startActivity(new Intent(ctx, com.example.dynamic_lib.DynamicLibActivity.class));
			Class<?> clazz = null;
			Activity activity = null;
			
			ClassLoader loader = MyLoader.getInstance().getLoader();
			// New instance of class which in dynamic loaded library.
			try {
				clazz = loader.loadClass("com.example.dynamic_lib.DynamicLibActivity");
				activity = (Activity)clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Toast.makeText(
					ctx,
					(activity != null? "Found": "Not found") + " dynamic class instance!",
					Toast.LENGTH_LONG
			).show();
			
			clazz = null;
			activity = null;
			// New instance of self class from loader.
			try {
				clazz = loader.loadClass("com.example.dynamic_load_sample.DynamicLoadSampleActivity");
				activity = (Activity)clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Toast.makeText(
					ctx,
					(activity != null? "Found": "Not found") + " self class instance!",
					Toast.LENGTH_LONG
			).show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_load_sample);
		
		findViewById(R.id.btnStartStaticLibActivity).setOnClickListener(mStartStaticLibActivity);
		findViewById(R.id.btnStartDynamicLibActivity).setOnClickListener(mStartDynamicLibActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dynamic_load_sample, menu);
		return true;
	}

}
