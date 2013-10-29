package com.example.dynamic_lib;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DynamicLibActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_lib);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dynamic_lib, menu);
		return true;
	}

}
