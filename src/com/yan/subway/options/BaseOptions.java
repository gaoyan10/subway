package com.yan.subway.options;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yan.subway.R;

public class BaseOptions {
	private static BaseOptions instance;
	private DisplayImageOptions bigOptions, smallOptions;
	private DisplayImageOptions bigOptionsDelay, smallOptionsDelay;
	private BaseOptions() {
		bigOptions = new DisplayImageOptions.Builder()
		//.showImageOnLoading(R.drawable.loadingpic)
		//.showImageForEmptyUri(R.drawable.loadingpic)
		//.showImageOnFail(R.drawable.loadingpic).cacheInMemory(true)
		.delayBeforeLoading(200)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.cacheOnDisk(true).build();
		smallOptions = new DisplayImageOptions.Builder()
		//.showImageOnLoading(R.drawable.ic_avatar_loading)
		//.showImageForEmptyUri(R.drawable.ic_avatar_loading)
		//.showImageOnFail(R.drawable.ic_avatar_loading).cacheInMemory(true)
		.delayBeforeLoading(200)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.cacheOnDisk(true).build();
		smallOptionsDelay = new DisplayImageOptions.Builder()
		//.showImageOnLoading(R.drawable.ic_avatar_loading)
		//.showImageForEmptyUri(R.drawable.ic_avatar_loading)
		.delayBeforeLoading(1000)
		.bitmapConfig(Bitmap.Config.RGB_565)
		//.showImageOnFail(R.drawable.ic_avatar_loading).cacheInMemory(true)
		.cacheOnDisk(true).build();
		bigOptionsDelay = new DisplayImageOptions.Builder()
		//.showImageOnLoading(R.drawable.loadingpic)
		//.showImageForEmptyUri(R.drawable.loadingpic)
		//.showImageOnFail(R.drawable.loadingpic).cacheInMemory(true)
		.delayBeforeLoading(1000)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.cacheOnDisk(true).build();
	}
	public DisplayImageOptions getBigOptions(boolean isDelay) {
		return isDelay ? bigOptionsDelay:bigOptions;
	}
	public DisplayImageOptions getSmallOptions(boolean isDelay) {
		return isDelay ? smallOptionsDelay:smallOptions;
	}
	public static BaseOptions getInstance() {
		if (instance == null) {
			instance = new BaseOptions();
		}
		return instance;
	}
}
