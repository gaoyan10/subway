package com.yan.subway.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;


public class SDUtils {
	public static int IO_ERROR = -2;
	public static int SUC = 0;
	public static int UNKOWN_ERROR = -1;
	public static int SD_ERROR = -3;
	public static int BUFF_SIZE = 1024 * 1024;
	public static boolean checkSDExit() {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true; //sd���ɶ���д.
		}else {
			return false;
		}
	}
	public static boolean checkEmptySize() {
		if (!checkSDExit()) {
			return false;
		}try {
		 File sdcardDir = Environment.getExternalStorageDirectory(); 
         StatFs sf = new StatFs(sdcardDir.getPath()); 
         long blockSize = sf.getBlockSize(); 
         long blockCount = sf.getBlockCount(); 
         long availCount = sf.getAvailableBlocks(); 
         long byteCount = blockSize * availCount; 
         return byteCount < (30 * 1024 * 1024) ? false: true;
		}catch(Exception e) {
			return false;
		}
	}
	public static boolean checkEmptySize(String path) {
		try{
		StatFs sf = new StatFs(path); 
        long blockSize = sf.getBlockSize(); 
        long blockCount = sf.getBlockCount(); 
        long availCount = sf.getAvailableBlocks(); 
        long byteCount = blockSize * availCount; 
        return byteCount < (30 * 1024 * 1024) ? false: true;
		}catch(Exception e){
			return false;
		}
	}
	public static boolean checkZeroFile(String path) {
		try {
		StatFs sf = new StatFs(path); 
        long blockSize = sf.getBlockSize(); 
        long blockCount = sf.getBlockCount(); 
        long byteCount = blockSize * blockCount; 
        return byteCount <= 0 ? false:true;
		}catch(Exception e) {
			return false;
		}
	}
	public static String getEmptySize(String path) {
		try {
		StatFs sf = new StatFs(path); 
        long blockSize = sf.getBlockSize(); 
        long blockCount = sf.getBlockCount(); 
        long availCount = sf.getAvailableBlocks(); 
        long availByte = blockSize * availCount; 
        long allByte = blockSize * blockCount;
        double avail = 0;
        double all = 0;
        String availString, allString;
        return "�ܹ�" + getSize(allByte) + "/����" + getSize(availByte); 
		}catch(Exception e) {
			return "�ܹ� 0MB/����0MB";
		}
	}
	public static long getAvailableSize(String path) {
		try {
		StatFs sf = new StatFs(path); 
        long blockSize = sf.getBlockSize(); 
        long blockCount = sf.getBlockCount(); 
        long availCount = sf.getAvailableBlocks(); 
        long availByte = blockSize * availCount; 
        return availByte;
		}catch(Exception e) {
			return 0;
		}
	}
	public static String getSize(long byteCount) {
		double all = 0;
		if (byteCount < (1000 * 1000 * 1000)) {
       	 	if(byteCount < 1000 * 1000) {
       	 		all = (double)(Math.round(((double)byteCount/(1000))*10)/10.0);
       	 		return all + "K";
       	 	}else {
       	 		all = (double)(Math.round(((double)byteCount/(1000 * 1000))*10)/10.0);
       	 		return all + "M";
       	 	}
        }else {
        	all = (double)(Math.round(((double)byteCount/(1000 * 1000 * 1000))*10)/10.0);
        	return all + "G";
        }
	}
	public static File getLogDir() {
		if (!checkSDExit()) {
			return null;
		} else{
			File dir = new File(Environment.getExternalStorageDirectory(), ConstantUtils.P_LOG);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir;
		}
	}
	public static int writeFileWithOutLog(String path, String name, String content, boolean append) {
		if (!checkSDExit()) {
			return SD_ERROR;  //sd��������.
		}
		File dir = new File(Environment.getExternalStorageDirectory(), path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		try {
			if (!file.exists()){
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch(IOException e) {
			return IO_ERROR;
		}
		return SUC;
	}
	public static int writeFile(String path, String name, String content, boolean append) {
		if (!checkSDExit()) {
			return SD_ERROR;  //sd��������.
		}
		File dir = new File(Environment.getExternalStorageDirectory(), path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		try {
			if (!file.exists()){
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch(IOException e) {
			//LogUtils.postErrorLog("IO" + e.getMessage());
			return IO_ERROR;
		}
		return SUC;
	}
	public static int writeFile(String path, String name, byte[] content) {
		if (!checkSDExit()) {
			return SD_ERROR;  //sd��������.
		}
		File dir = new File(Environment.getExternalStorageDirectory(), path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
			writer.write(content);
			writer.close();
		}catch(IOException e) {
			//LogUtils.postErrorLog("IO" + e.getMessage());
			return IO_ERROR;
		}
		return SUC;
	}
	public static String readSDFile(String path, String name) {
		return readFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path, name);
	}
	public static String readFile(String path, String name) {
		File file = new File (path, name);
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String content = reader.readLine();
			while (content != null) {
				sb.append(content);
				sb.append('\n');
				content = reader.readLine();
			}
			reader.close();
			return sb.toString();
		}catch (IOException e) {
			//LogUtils.postErrorLog("IO" + e.getMessage());
			return "";
		}
	}
	public static String readSDFile(String path, String name, String encode) {
		File file = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path, name);
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), encode);
			BufferedReader reader = new BufferedReader(isr);
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String content = reader.readLine();
			while (content != null) {
				sb.append(content);
				sb.append('\n');
				content = reader.readLine();
			}
			reader.close();
			return sb.toString();
		}catch (IOException e) {
			//LogUtils.postErrorLog("IO" + e.getMessage());
			return "";
		}
	}
	public static int writeStreamToSDCard(String path,String filename,InputStream input) {
		if (!checkSDExit()) {
			return SD_ERROR;  //sd��������.
		}
        OutputStream output=null;
        File dir = new File(Environment.getExternalStorageDirectory(), path);
        if (!dir.exists()) {
        	dir.mkdirs();
        }
        File file = new File (dir, filename);
        try {
        	if(!file.exists()) {
        		file.createNewFile();
        	}
            output=new FileOutputStream(file);
            byte[]bt=new byte[4*1024];
            while (input.read(bt)!=-1) {
                output.write(bt);
            }
            //ˢ�»��棬
            output.flush();
            output.close();
        } catch (IOException e) {
        	//LogUtils.postErrorLog("IO" + e.getMessage());
            e.printStackTrace();
            return IO_ERROR;
        }
        finally{
           try{
        	   output.close();
           }catch (Exception e) {
        	   //LogUtils.postErrorLog("IO" + e.getMessage());
                 e.printStackTrace();
              }
          }
         return 0;
	}
	public static boolean checkFile(String path, String name) {
		File file = new File(path, name);
		if (file.exists() && file.length() > 0) {
			return true;
		}else{
			return false;
		}
	}
	public static boolean checkSDFile(String path, String name) {
		if(checkSDExit()) {
			File f = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + path, name);
			if (f.exists() && f.length() > 0) {
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	public static File getSDFile(String parent, String filename) {
		File dir = new File(Environment.getExternalStorageDirectory(), parent);
		if (!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir, filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				//LogUtils.postErrorLog("IO" + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}
	public static boolean renameFile(File oldFile, String newName) {
		File file = new File(newName);
		try {
			file.createNewFile();
			oldFile.renameTo(file);
			return true;
		}catch(IOException e) {
			//LogUtils.postErrorLog("IO" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public static boolean deleteSDFile(String path, String name) {
		File file = new File(Environment.getExternalStorageDirectory(), path + File.separator + name);
		if (file.exists()) {
			file.delete();
		}
		return true;
	}
	public static Collection getSDFiles(String path) {
    	File dir = new File(Environment.getExternalStorageDirectory(), path);
		if (!dir.exists()) {
			return null;
		}
		List list = new ArrayList();
		File[] files = dir.listFiles();
		for(File f : files) {
			list.add(f);
		}
		return list;
    }
	private static void zipFile(File resFile, ZipOutputStream zipout,
			String rootpath) throws FileNotFoundException, IOException {
		rootpath = rootpath
				+ (rootpath.trim().length() == 0 ? "" : File.separator)
				+ resFile.getName();
		rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
		if (resFile.isDirectory()) {
			File[] fileList = resFile.listFiles();
			for (File file : fileList) {
				zipFile(file, zipout, rootpath);
			}
		} else {
			byte buffer[] = new byte[BUFF_SIZE];
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(resFile), BUFF_SIZE);
			zipout.putNextEntry(new ZipEntry(rootpath));
			int realLength;
			while ((realLength = in.read(buffer)) != -1) {
				zipout.write(buffer, 0, realLength);
			} 
			in.close();
			zipout.flush();
			zipout.closeEntry();
		}
	}

	public static void zipFiles(Collection<File> resFileList, File zipFile,
			String comment) throws IOException {
		ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(zipFile), BUFF_SIZE));
		for (File resFile : resFileList) {
			zipFile(resFile, zipout, "");
		}
		zipout.setComment(comment);
		zipout.close();
	}
	public static boolean checkBOM() {
		return false;
	}
}

