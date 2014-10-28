package com.yan.subway;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
	public static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this.getApplicationContext();
		initImageLoader(this.getApplicationContext());
	}
	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 1)
				.threadPoolSize(5).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileCount(500).memoryCacheSize(5 * 1024 * 1024)
				.memoryCacheSizePercentage(20).diskCacheSize(100 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// .writeDebugLogs() // Not necessary in common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	} 
}
