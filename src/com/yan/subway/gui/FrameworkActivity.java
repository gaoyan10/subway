package com.yan.subway.gui;

import com.yan.subway.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.yan.subway.BaseActivity;

public class FrameworkActivity extends BaseActivity {
	private MainSlidingMenu menu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_framework);
		onCreateView();
		setOnActionBar(R.string.title_main);
	}
	@Override
	public void onCreateView() {
		// TODO Auto-generated method stub
		menu = new MainSlidingMenu(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks o  n the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id) {
		case android.R.id.home:
			menu.toggle();
			return true;
		case R.id.action_settings:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
