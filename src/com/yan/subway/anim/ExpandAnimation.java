package com.yan.subway.anim;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ExpandAnimation extends Animation {
	private View mAnimatedView;
	private RelativeLayout.LayoutParams mViewLayoutParams;
	private int mMarginStart, mMarginEnd;
	private boolean mIsVisibleAfter = false;
	private boolean mWasEndedAlready = false;
	private boolean noDuration = false;

	/**
	 * Initialize the animation
	 * 
	 * @param view
	 *            The layout we want to animate
	 * @param duration
	 *            The duration of the animation, in ms
	 */
	public ExpandAnimation(View view, int duration, boolean noDuration) {
		this.noDuration = noDuration;
		setDuration(duration);
		mAnimatedView = view;
		mViewLayoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

		// if the bottom margin is 0,
		// then after the animation will end it'll be negative, and invisible.
		mIsVisibleAfter = (mViewLayoutParams.bottomMargin == 0);
		mMarginStart = mViewLayoutParams.bottomMargin;
		mAnimatedView.setVisibility(View.VISIBLE);
		mMarginEnd = (mMarginStart == 0 ? (0 - view.getHeight()) : 0);
	}
	public void show(Context context) {
		//Toast.makeText(context, mIsVisibleAfter + " " + mMarginStart + " " + mMarginEnd + " " + mWasEndedAlready, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		//Log.i("test", interpolatedTime + " ");
		if (interpolatedTime < 1.0f && !noDuration) {

			// Calculating the new bottom margin, and setting it
			mViewLayoutParams.bottomMargin = mMarginStart
					+ (int) ((mMarginEnd - mMarginStart) * interpolatedTime);

			// Invalidating the layout, making us seeing the changes we made
			mAnimatedView.requestLayout();

			// Making sure we didn't run the ending before (it happens!)
		} else if (!mWasEndedAlready) {
			mViewLayoutParams.bottomMargin = mMarginEnd;
			

			if (mIsVisibleAfter) {
				mAnimatedView.setVisibility(View.GONE);
			}
			mAnimatedView.requestLayout();
			mWasEndedAlready = true;
		}
	}
}
