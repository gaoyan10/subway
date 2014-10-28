package com.yan.subway.ui;

import com.yan.subway.util.Utils;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * SideBar for nevigation.
 * Items maybe letters or icons.
 * @author gaoyansansheng@gmail.com
 *
 */
public class SideBar extends View {
	public static final String TAG = SideBar.class.getName();
	private Context context;
	private int oldlocation = 0;
	private Bitmap oldBitmap;
	public SideBar(Context context) {
		super(context);
		init(context);
	}
	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public void init(Context context) {
		this.context = context;
	}
	private float itemHeight = -1;
	private Paint paint;
	private Object[] objects;
	private Bitmap bitmap;
	private OnItemTouchListener touchListener;
	@Override
	protected void onDraw(Canvas canvas) {
		if (objects == null) {
			return;
		}
		if (itemHeight == -1) {
			itemHeight = getHeight() / objects.length / 2;
		}
		if (paint == null) {
			paint = new Paint();
			paint.setTextSize(itemHeight - 4);
			paint.setColor(this.getResources().getColor(R.color.black));
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		}else{
			Canvas mCanvas = new Canvas();
			bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
			mCanvas.setBitmap(bitmap);
			float widthCenter = this.getMeasuredWidth() / 2;
			for(int i = 0; i < objects.length; i ++) {
				if (objects[i] instanceof String) {
					mCanvas.drawText((String)objects[i], widthCenter - paint.measureText((String)objects[i]) / 2, itemHeight * (i + 1), paint);
					
				}
				if (objects[i] instanceof Bitmap) {
					Bitmap item = (Bitmap)objects[i];
					int h = item.getHeight();
					int w = item.getWidth();
					int width = this.getMeasuredWidth();
					int tmp = width;
					int px = Utils.dip2px(context, 16);
					width = width > px?px :width;
					int top = (int)(itemHeight * 2 * i + itemHeight * 0.5);
					int height = (int)(top + h / w * width);
					mCanvas.drawBitmap((Bitmap)objects[i], null , new Rect((tmp - width) / 2, top, width + (tmp - width) / 2, height), paint);
				}
			}
		}
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, 0, 0, paint);
		}
		super.onDraw(canvas);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//super.onTouchEvent(event);
		//Toast.makeText(context, "test2", Toast.LENGTH_SHORT).show();
		if (this.touchListener == null || objects == null) {
			return false;
		}
		//Log.i("testtest", event.getAction() + " ");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			//Toast.makeText(context, "test4", Toast.LENGTH_SHORT).show();
			int position = (int)(event.getY() / itemHeight / 2);
			if (position >= 0 && position < objects.length) {
				touchListener.onItemTouch(objects[position], position);
			}
			return true;
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			//Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
			touchListener.onActionUp();
			return true;
		}
		return true;
	}
	public void setShowObject(Object[] item) throws Exception{
		for(Object obj : item) {
			if (!(obj instanceof String) && !(obj instanceof Bitmap)) {
				throw new Exception("object only be String or Bitmap");
			}
		}
		this.objects = item;
		
	}
	public void setOnItemTouchListener(OnItemTouchListener listener) {
		this.touchListener = listener;
	}
	public interface OnItemTouchListener {
		public void onItemTouch(Object object, int position);
		public void onActionUp();
	}
	public void changeBitmapIcon(int loc, Bitmap bitmap) {
		if (loc >= objects.length) {
			return;
		}
		if (oldBitmap != null) { 
			objects[oldlocation] = oldBitmap;
		}
		oldlocation = loc;
		oldBitmap = (Bitmap)objects[loc];
		objects[loc] = bitmap;
		this.postInvalidate();
	}

}
