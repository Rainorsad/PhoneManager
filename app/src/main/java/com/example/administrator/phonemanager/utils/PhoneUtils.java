package com.example.administrator.phonemanager.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;

import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.bean.PhoneCleanBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhoneUtils {

	public static void deleteFile(File f){
		if (f.isDirectory()){
			File[] listFiles = f.listFiles();
			if (listFiles !=null){
				for (int i=0;i<listFiles.length;i++){
					if (listFiles[i]!=null){
						if (listFiles[i].isDirectory()){
							deleteFile(listFiles[i]);
						}else {
							listFiles[i].delete();
						}
					}
				}
			}
		}
	}
	/**
	 * 计算文件大小
	 * @param f
	 * @return
     */
	public static long fileSize(File f){
		long size = 0;
		if (!f.isDirectory()){
			return f.length();
		}else {
			File[] files = f.listFiles();
			if (files!=null){
				for (int i=0;i<files.length;i++){
					if (files[i]!=null){
						if (files[i].isDirectory()){
							size =size+fileSize(files[i]);
						}else {
							size = size+ files[i].length();
						}
					}
				}
			}
		}
		return size;
	};
	/**
	 * 计算垃圾
	 * @param path
	 * @param context
     * @return
     */
	public static List<PhoneCleanBean> getGarbageInfo(String path,Context context){
		PackageManager pm = context.getPackageManager();
		String buildpath = PhoneUtil.getPhoneInsid();
		List<PhoneCleanBean> phoneCleanBeen = new ArrayList<>();
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		if (db.isOpen()){
			String[] colums = new String[]{"_id","softChinesename","apkname","filepath"};
			Cursor c = db.query("softdetail",colums,null,null,null,null,null);
			while (c.moveToNext()){
				int id_index = c.getColumnIndex("_id");
				int name_index = c.getColumnIndex("softChinesename");
				int apk_index = c.getColumnIndex("apkname");
				int filepath_index = c.getColumnIndex("filepath");
				int id = c.getInt(id_index);
				String name = c.getString(name_index);
				String apk = c.getString(apk_index);
				String filepath = c.getString(filepath_index);
				PhoneCleanBean phoneCleanBean = new PhoneCleanBean();
				phoneCleanBean.setId(id);
				phoneCleanBean.setFilePath(filepath);
				phoneCleanBean.setName(name);
				phoneCleanBean.setPackname(apk);
				phoneCleanBean.setFilePath(buildpath+filepath);
				File f = new File(phoneCleanBean.getFilePath());
				if (f.exists()){
					Drawable icon = null;
					try {
						icon = pm.getApplicationIcon(phoneCleanBean.getPackname());

					} catch (PackageManager.NameNotFoundException e) {
						e.printStackTrace();
					}
					if (icon == null){
						icon = context.getResources().getDrawable(R.mipmap.ic_launcher);
					}
					phoneCleanBean.setDrawable(icon);
					phoneCleanBeen.add(phoneCleanBean);
				}
			}
		}
		return phoneCleanBeen;
	}

	/**
	 * 拷贝sd卡
	 * @param is
	 * @param path
	 * @throws IOException
     */
	public static void CopyDb(InputStream is ,String path) throws IOException {
		File f = new File(path);
		if (f.exists()){
			return;
		}
		BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(f));
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = bufferedInputStream.read(b))!= -1){
			bufferedOutputStream.write(b,0,len);
		}
		bufferedOutputStream.flush();
		bufferedInputStream.close();
		bufferedOutputStream.close();
	}

	/**
	 *密度转换像素
	 * @param context
	 * @param dp
     * @return
     */
	public static int dp2px(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 像素转换密度
	 * @param context
	 * @param px
     * @return
     */
	public static int px2dp(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 图片压缩方法
	 * @param pathName
	 * @param h
	 * @param w
	 * @param context
     * @return
     */
	public static Bitmap loadBitmap(String pathName, int h, int w,
			Context context) {
		int targetw = dp2px(context, w);
		int targeth = dp2px(context, h);
		Options options = new Options();
		options.inJustDecodeBounds = true;//值为true时，decode会返回null
		BitmapFactory.decodeFile(pathName, options);
		int resw = options.outWidth;
		int resh = options.outHeight;
		// 40x40
		if (resw <= targetw && resh <= targeth) {
			//对大图片进行压缩，压缩比例
			options.inSampleSize = 1;
		}
		else {
			int scalew = resw / targetw;
			int scaleh = resh / targeth;
			int scale = scalew > scaleh ? scalew : scaleh;
			options.inSampleSize = scale;
		}
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
		return bitmap;
	}
}
