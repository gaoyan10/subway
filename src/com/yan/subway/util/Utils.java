package com.yan.subway.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

import com.yan.subway.BaseApplication;

public class Utils {
	private final String TAG = Utils.class.getSimpleName();
	public static int CMNET = 1;
	public static int CMWAP = 2;
	public static int WIFI = 3;

	public Utils() {
	}

	public static String getAPIKey(Context c) {
		ApplicationInfo appInfo;
		String key = null;
		/*try {
			appInfo = c.getPackageManager().getApplicationInfo(
					c.getPackageName(), PackageManager.GET_META_DATA);
			key = appInfo.metaData.getString(ConstantUtils.KEY);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			LogUtils.postErrorLog("WARINNING THIS MAYBE AN ERROR CLIENT"
					+ e.getMessage());
			e.printStackTrace();
		}
		if (key != null) {
			return key;
		} else {
			return "";
		}*/
		return null;
	}
	
	public static String getSecretToken(Context c, String filename) {
		PreferenceUtils pre = new PreferenceUtils(c, filename);
		//String src = easyDecode(pre.getPreference(ConstantUtils.ACCESS, ""));
		//pre.putPreference(ConstantUtils.ACCESS, easyEncode(src));
		//return src;
		return null;
	}

	public static String easyEncode(String source) {
		int length = source.length();
		char[] des = new char[length + 2];
		char[] seed = new char[2];
		Random random = new Random();
		seed[0] = (char) random.nextInt();// % Character.MAX_VALUE;
		seed[1] = (char) random.nextInt();
		for (int i = 0; i < length; i += 2) {
			des[i] = (char) (seed[0] ^ source.charAt(i));
			des[i + 1] = (char) (seed[1] ^ source.charAt(i));
		}
		return new String(des) + seed;

	}

	public static String getImieStatus(Context c) {
		TelephonyManager tm = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		if (deviceId == null) {
			return "null deviceid";
		}
		return deviceId;
	}

	public static String getAndroidId(Context c) {
		String androidId = Secure.getString(c.getContentResolver(),
				Secure.ANDROID_ID);
		return androidId;
	}

	public static String easyDecode(String des) {
		int length = des.length();
		if (length <= 2) {
			return "";
		} else {
			char[] source = new char[length - 2];
			char[] seed = new char[2];
			seed[0] = des.charAt(length - 2);
			seed[1] = des.charAt(length - 1);
			for (int i = 0; i < length; i += 2) {
				source[i] = (char) (seed[0] ^ des.charAt(i));
				source[i + 1] = (char) (seed[1] ^ des.charAt(i));
			}
			return source.toString();
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static boolean checkEmail(String email) {
		// String check =
		// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		String check = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	public static String getLanguage() {
		String lan = Locale.getDefault().getLanguage();
		if (lan.equals("zh") || lan.equals("zh_CH") || lan.equals("zh_HK")
				|| lan.equals("zh_TW")) {
			return "zh";
		} else {
			return lan;
		}
	}

	public static boolean isVideoUrl(String url) {
		try {
			if (url == null) {
				return false;
			}
			Pattern p = Pattern
					.compile("^(http://|https://)?(.*)?.(mp4|MP4|3gp|3GP)$");
			Matcher m = p.matcher(url);
			if (m.matches()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean isUrl(String url) {
		try {
			if (url == null) {
				return false;
			}
			Pattern p = Pattern
					.compile("^((https?|ftp|news)://)?([a-z]([a-z0-9\\-]*[\\.。])+([a-z]{2}"
							+ "|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)"
							+ "|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"
							+ ")(/[a-z0-9_\\-\\.~]+)*(/([a-z0-9_\\-\\.]*)(\\?[a-z0-9+_\\-\\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$");
			Matcher m = p.matcher(url);
			if (m.matches()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean isBackground(final Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;

	}

	public static void setAutoBrightness(boolean auto, Context context) {
		if (!auto) {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		} else {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
		}
	}

	public static boolean getAutoBrightness(Context context) {
		try {
			return Settings.System.getInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ? true
					: false;
		} catch (Exception e) {
			return false;
		}
	}

	public static int getNetType(Context context) {
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
				netType = CMNET;
			} else {
				netType = CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = WIFI;
		}
		return netType;
	}

	public static int getDensity(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();

		float density = dm.density;
		int densityDPI = dm.densityDpi;
		return densityDPI;
	}

	public static double screenCount(Context context) {
		int d = getDensity(context);
		if (d <= 240 && d >= 160) {
			return 4.4;
		} else {
			return 4.1;
		}
	}

	/**
	 * date 2014-04-20
	 * 
	 * @param date
	 * @return
	 */
	public static long date2timeStamp(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date t = sdf.parse(date);
			return t.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	public static void getScreenMessage(Activity context) {
		Display display = context.getWindowManager().getDefaultDisplay();
		ConstantUtils.SCREEN_HEIGHT = display.getHeight();
		ConstantUtils.SCREEN_WIDTH = display.getWidth();
	}

	public static String getFormatTime(int time) {
		try {
			time /= 1000;
			int minute = time / 60;
			int hour = minute / 60;
			int second = time % 60;
			minute %= 60;
			return String.format("%02d:%02d:%02d", hour, minute, second);
		} catch (Exception e) {
			return time + "";
		}
	}

	public static String getSystemMinite() {
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		return format.format(new Date());
	}

	public static int[] getTenday(Date future) {
		long f = future.getTime();
		long p = f - System.currentTimeMillis();
		int one = (int) p / (1000 * 3600 * 24);
		int ten = one / 10;
		one = one % 10;
		int[] time = new int[] { ten, one };
		return time;

	}

	public static long dateToTimestamp(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static int getStatusHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int height = frame.top;
		if (height <= 0) {
			   Class<?> c = null;
			   Object obj = null;
			   Field field = null;
			   int x = 0, sbar = 0; 
			   try {
			       c = Class.forName("com.android.internal.R$dimen");
			       obj = c.newInstance();
			       field = c.getField("status_bar_height");
			       x = Integer.parseInt(field.get(obj).toString());
			       sbar = activity.getResources().getDimensionPixelSize(x);
			       height = sbar;
			 
			} catch (Exception e1) {
			      e1.printStackTrace(); 
			}  
		}
		return height;
	}

	public static boolean isPast(String time) {
		long t = dateToTimestamp(time);
		int offset = TimeZone.getDefault().getRawOffset();
		long client = System.currentTimeMillis() - offset;
		long server = t - 8 * 1000 * 3600;
		return client - 1000 * 60 > server ? true : false;

	}
	public static boolean isPast(String time, long off) {
		long t = dateToTimestamp(time);
		int offset = TimeZone.getDefault().getRawOffset();
		long client = System.currentTimeMillis() - offset;
		long server = t - 8 * 1000 * 3600;
		return client - 1000 * 60> server - off ? true : false;
	}
	public static boolean checkCourseStatus(String start, boolean def) {
		int offset = TimeZone.getDefault().getRawOffset();
		long client = System.currentTimeMillis() - offset;
		Date now = new Date(client);
		String time = start;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int status = 0;
		boolean futures = def;
		Date date;
		// int [] tt = new int[2];
		try {
			date = sdf.parse(time);
			futures = date.after(now);
			// tt = Utils.getTenday(date);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return futures;
	}

	public static boolean isPast(long time) {
		int offset = TimeZone.getDefault().getRawOffset();
		long client = System.currentTimeMillis() - offset;
		long server = time - 8 * 1000 * 3600;
		return client - 1000 * 60 > server ? true : false;
	}

	public static boolean isStartPast(String time) {
		time = time.replace("T", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");
		try {
			Date date = sdf.parse(time);
			return isPast(date.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static String timeShort(String lon) {
		if (lon == null) {
			return null;
		}
		if (lon.length() <= 10) {
			return lon;
		} else {
			return lon.substring(2, 11);
		}
	}

	public static boolean checkPackage(String packName) {
		boolean flag = false;
		PackageManager manager = BaseApplication.mContext.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> apps = manager.queryIntentActivities(
				mainIntent, 0);
		PackageInfo info = null;
		for (ResolveInfo app : apps) {
			String pkg = app.activityInfo.packageName;
			String cls = app.activityInfo.name;
			if (pkg.equals(packName)) {
				flag = true;
				try {
					info = manager.getPackageInfo(pkg, 0);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				String mVersonName = info.versionName;
				int versionCode = info.versionCode;
				break;
			}
		}
		// Toast.makeText(UIApplication.mContext, "test" + flag,
		// Toast.LENGTH_SHORT).show();
		return flag;
	}

	public static int getNumberCPUByRuntime() {
		return Runtime.getRuntime().availableProcessors();
	}

	public static int getNumberCPUByFile() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}

		}

		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			// Log.d(TAG, "CPU Count: "+files.length);
			// Return the number of cores (virtual CPU devices)
			return files.length;
		} catch (Exception e) {
			// Print exception
			// Log.d(TAG, "CPU Count: Failed.");
			e.printStackTrace();
			// Default to return 1 core
			return 1;
		}

	}
}
