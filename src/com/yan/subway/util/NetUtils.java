package com.yan.subway.util;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import com.yan.subway.BaseApplication;
import com.yan.subway.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.Toast;

public class NetUtils {
	public static final int CMNET = 3;
	public static final int CMWAP = 2;
	public static final int WIFI = 1;
	public static final int UNKNOW = -1;
	private static boolean isDoing = false;
	public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	public static String getLocaldeviceId(Context _context) {
		TelephonyManager tm = (TelephonyManager) _context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		if (deviceId == null || deviceId.trim().length() == 0) {
			deviceId = String.valueOf(System.currentTimeMillis());
		}
		return deviceId;
	}

	
	
	public static int getAPNType(Context context) {
		int netType = UNKNOW;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}else{
			if (!networkInfo.isAvailable()) {
				return netType;
			}
		}
		int nType = networkInfo.getType();
		System.out.println("networkInfo.getExtraInfo() is "
				+ networkInfo.getExtraInfo());
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			try{
			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
				netType = CMNET;
			} else {
				netType = CMWAP;
			}}catch(Exception e){
				e.printStackTrace();
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = WIFI;
		}
		return netType;
	}

	public static String getAPNTypeString(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo == null) {
			return "unknown";
		}
		int nType = networkInfo.getType();
		return networkInfo.getExtraInfo() + " " + nType;
	}

	public static String checkNetStatus(Context context, String error) {
		if (getAPNType(context) == UNKNOW) {
			return context.getString(R.string.net_error);
		}
		return error;
	}

	public static String getDeviceLog(Context context) {
		return getAPNTypeString(context) + "\n" + getLocalMacAddress(context)
				+ "\n" + getLocaldeviceId(context) + "\n" + getVersionMessage(context);
	}
	public static String getVersionMessage(Context context) {
		try {
			PackageManager pm = context.getPackageManager();    
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);   
			String versionName = pi.versionName;    
			int versionCode = pi.versionCode;  
			if (versionName == null || versionName.length() <= 0) {    
				return "";    
			}else {
				return versionName + " " + versionCode;
			}
		}catch (Exception e) {    
        	return "";
        }
	}
	public long getUidRxBytes(){ //获取总的接受字节数，包含Mobile和WiFi等
        PackageManager pm = BaseApplication.mContext.getPackageManager();
        ApplicationInfo ai = null;
           try {
                   ai = pm.getApplicationInfo("cn.wangan.bnaction", PackageManager.GET_ACTIVITIES);
           } catch (NameNotFoundException e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
           }
       return TrafficStats.getUidRxBytes(ai.uid)==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024);
       }
	public static String getLocalIpAddress() { 
		try { 
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) { 
				NetworkInterface intf = (NetworkInterface) en.nextElement(); 
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) { 
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement(); 
					if (!inetAddress.isLoopbackAddress()) { 
						return inetAddress.getHostAddress().toString();
					}
				} 
		 
			}
		}catch(Exception ex) { 
			//Log.e("error", ex.toString()); 
		} 
		return ""; 
	}
}