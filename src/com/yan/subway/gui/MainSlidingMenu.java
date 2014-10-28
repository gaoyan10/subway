package com.yan.subway.gui;

import android.graphics.Canvas;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yan.subway.R;

public class MainSlidingMenu extends SlidingMenu {
	private FrameworkActivity mContext;
	public MainSlidingMenu(FrameworkActivity activity) {
		super(activity);
		mContext = activity;
		init();
	}
	public void init() {
		this.setMode(SlidingMenu.LEFT_RIGHT);    
		this.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		this.setBehindWidthRes(R.dimen.shadow_width);
		this.setShadowDrawable(R.drawable.shadow);
		this.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		this.setFadeDegree(0.35f);
		this.attachToActivity(mContext, SlidingMenu.SLIDING_WINDOW);
		CanvasTransformer canvas = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.scale(percentOpen, 1, 0, 0);
			}
		};
		this.setMenu(R.layout.slidingmenu_main);
		this.setSecondaryMenu(R.layout.slidingmenu_right);
		initView();
	}
	/**
	 * init view in this method.
	 */
	private void initView() {
		
	}
}
