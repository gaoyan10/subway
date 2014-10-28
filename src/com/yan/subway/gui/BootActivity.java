package com.yan.subway.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yan.subway.R;

public class BootActivity extends Activity {
	private TextView hello;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hello = (TextView) findViewById(R.id.activity_boot_hello);
		hello.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BootActivity.this, FrameworkActivity.class);
				BootActivity.this.startActivity(intent);
				BootActivity.this.finish();
			}			
		});
	}
}
