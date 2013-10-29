package com.example.dynamic_load_sample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class DynamicLoadSampleActivity extends Activity {

	private OnClickListener mStartStaticLibActivity = new OnClickListener() {
		
		public void onClick(View v) {
			Context ctx = v.getContext();
			ctx.startActivity(new Intent(ctx, com.example.static_lib.StaticLibActivity.class));
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_load_sample);
		
		findViewById(R.id.btnStartStaticLibActivity).setOnClickListener(mStartStaticLibActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dynamic_load_sample, menu);
		return true;
	}

}
