package com.yan.subway.ui;

import com.yan.subway.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoadingLayout {
	private Context context;
	private LinearLayout layout;
	private ImageView iv;
	public LoadingLayout(Context context) {
		this.context = context;
		layout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.item_loading, null);
		iv = (ImageView)layout.findViewById(R.id.item_loading_icon);
		iv.setVisibility(View.GONE);
	}
	public LinearLayout createLoading() {
		return layout;
	}
	public void showLoading() {
		iv.setVisibility(View.VISIBLE);
		//hideLoading();
	}
	public void hideLoading() {
		Animation anim = new TranslateAnimation(1.0f, 1.0f, 0, 1.0f);
		anim.setFillAfter(true);
		anim.setDuration(800);
		anim.setStartOffset(800);
		anim.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				iv.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		iv.clearAnimation();
		iv.startAnimation(anim);
	}

}
