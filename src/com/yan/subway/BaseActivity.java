package com.yan.subway;

import com.yan.subway.R;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {
	public abstract void onCreateView();
	public void setOnActionBar(int resId) {
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		//actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setTitle(resId);
		actionBar.setIcon(R.drawable.nva_return);
		actionBar.setLogo(R.drawable.ic_launcher);
	}
}
