package com.example.administrator.phonemanager.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.bean.ModelBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/25.
 */
public class PhoneUtil {
    //获得手机品牌
    public static String getPhNme(){
        return android.os.Build.BRAND;
    }
    //获得手机型号
    public static String getVersion(){
        return Build.MODEL + " " + Build.VERSION.RELEASE;
    }
    //获得手机最大内存
    public static long getMaxMemory() throws IOException {
        String str = "/proc/meminfo";// 系统内存信息文件
        String stt;
        String [] bytes;
        FileReader fileReader = new FileReader(str);
        BufferedReader bufferedReader = new BufferedReader(fileReader,8192);
        stt  = bufferedReader.readLine(); // 读取meminfo第一行，系统总内存大小
        bytes = stt.split("\\s+");
        long num = Integer.valueOf(bytes[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
        bufferedReader.close();
        return num;
    }
    //获得手机空闲内存
    public static long getFreeMemory(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }
    //获取手机进程
    public static List<ModelBean> getThread(Context context){
        List<ModelBean> modelBeen = new ArrayList<>();
        PackageManager pm = context.getPackageManager();//包名管理器
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  //activity管理器
        //此方法4.4版本适用，经测试6.0版本适用，5.0版本不适用
        List<ActivityManager.RunningAppProcessInfo> runAPInfos = activityManager.getRunningAppProcesses();
        for (int i=0;i<runAPInfos.size();i++){
            ModelBean modelBean = new ModelBean();
            ActivityManager.RunningAppProcessInfo appinfo = runAPInfos.get(i);
            int pid = appinfo.pid; //获得pid
               modelBean.setPid(pid);
            String packname = appinfo.processName; //获得包名
               modelBean.setPackname(packname);
            try {
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packname,0); //区分用户进程或者系统进程
                Drawable drawable = applicationInfo.loadIcon(pm); //获得图片
                if (drawable == null){
                    Drawable drawable1 = context.getResources().getDrawable(R.mipmap.ic_launcher);
                    modelBean.setImagename(drawable1);
                }else {
                    modelBean.setImagename(drawable);
                }
                String name = applicationInfo.loadLabel(pm).toString(); // 获得名称
                modelBean.setThreadname(name);
                int check = isUserApp(applicationInfo);
                modelBean.setType(check);
            } catch (Exception e) {
                e.printStackTrace();
                Drawable drawable1 = context.getResources().getDrawable(R.mipmap.ic_launcher);
                modelBean.setImagename(drawable1);
                modelBean.setThreadname(packname);
                modelBean.setPackname(packname);
                modelBean.setType(2);
            }
            android.os.Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{pid});
            android.os.Debug.MemoryInfo memoryInfo = memoryInfos[0];
            modelBean.setThreadsql(memoryInfo.getTotalPrivateDirty());//获得内存
            modelBeen.add(modelBean);
        }
        return modelBeen;
    }
    //判断是否为用户进程
    public static int isUserApp(ApplicationInfo applicationInfo){
        if ((applicationInfo.flags & applicationInfo.FLAG_UPDATED_SYSTEM_APP)!= 0){
            return 1;
        }else if ((applicationInfo.flags & applicationInfo.FLAG_SYSTEM) == 0){
            return 1;
        }
        return 2;
    }

    //杀死进程  此方法中被杀掉的进程和当前进程处于同一个包或者应用程序中，或者将被杀掉的进程是由当前应用程序所创建的附加进程，或者被杀掉的进程和当前进程共享了普通用户的UID
    public static void killApp(List<ModelBean> usertaskInfo) {
        // TODO Auto-generated method stub
        for(int i=0;i<usertaskInfo.size();i++){
            ModelBean taskInfo = usertaskInfo.get(i);
            int pid = taskInfo.getPid();
            if(pid!=android.os.Process.myPid()&&taskInfo.getIsselect()==true){
                android.os.Process.killProcess(pid);
            }
        }
    }
    //杀死其他app
    public static void killOtherApp(List<ModelBean> usertaskInfo,Context context){
        for (int i=0;i<usertaskInfo.size();i++){
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ModelBean modelBean = usertaskInfo.get(i);
            String packname = modelBean.getPackname();
            int pid = modelBean.getPid();
            if (pid!=android.os.Process.myPid()&&modelBean.getIsselect()==true){
                activityManager.killBackgroundProcesses(packname);
            }
        }
    }
    //获得手机外置空间路径
    public static String getPhoneOutPath(){
        Map<String,String> getin = System.getenv();
        if (getin.containsKey("SECONDARY_STORAGE")){
            String s = getin.get("SECONDARY_STORAGE");
            String[] split = s.split(":");
            return split[0];
        }
        return null;
    }
    //获得手机内置空间路径
    public static String getPhoneInsid(){
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }
    //计算内置可用内存大小
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getInsideMemoryFree(){
        String inpath = getPhoneInsid();
        StatFs sf = new StatFs(inpath);
        int i = getPhonrAPI();
        long blockSize;
        long availableBlocks;
        if (i<18){
            blockSize = sf.getBlockSize();
            availableBlocks = sf.getAvailableBlocks();
        }else {
            blockSize = sf.getBlockSizeLong(); //返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
            availableBlocks = sf.getAvailableBlocksLong(); //获得当前可用的内存空间
        }
        return blockSize*availableBlocks;
    }
    //计算内置最大空间
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getInsideMemoryMax(){
        String inpath = getPhoneInsid();
        StatFs sf = new StatFs(inpath);
        int i = getPhonrAPI();
        long blockSize;
        long BlockCount;
        if (i<18){
            blockSize = sf.getBlockSize();//返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
            BlockCount = sf.getBlockCount(); //获取该区域可用的文件系统数
        }else {
            blockSize = sf.getBlockSizeLong();//返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
            BlockCount = sf.getBlockCountLong(); //获取该区域可用的文件系统数
        }
        return blockSize*BlockCount;
    }
    //计算外置可用内存大小
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getOutMemoryFree(){
        String s = getPhoneOutPath();
        if (s ==null){
            return 0;
        }else {
            StatFs sf = new StatFs(s);
            int i = getPhonrAPI();
            long blockSize;
            long availableBlocks;
            if (i<18){
                blockSize = sf.getBlockSize();//返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
                availableBlocks = sf.getAvailableBlocks(); //获得当前可用的内存空间
            }else {
                blockSize = sf.getBlockSizeLong();//返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
                availableBlocks = sf.getAvailableBlocksLong(); //获得当前可用的内存空间
            }
            return blockSize*availableBlocks;
        }
    }
    //计算外置最大内存
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getOutMemoryMax(){
        String inpath = getPhoneOutPath();
        if (inpath == null){
            return 0;
        }else {
            StatFs sf = new StatFs(inpath);
            int i = getPhonrAPI();
            long blockSize;
            long BlockCount;
            if (i<18){
                blockSize = sf.getBlockSize();//返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
                BlockCount = sf.getBlockCount(); //获取该区域可用的文件系统数
            }else {
                blockSize = sf.getBlockSizeLong();//返回文件系统一个块的大小，单位byte.此方法API18版本前没有，需要用getBlockSize
                BlockCount = sf.getBlockCountLong(); //获取该区域可用的文件系统数
            }
            return blockSize*BlockCount;
        }
    }

    //获取手机api型号
    public static int getPhonrAPI(){
        int api;
        api = Build.VERSION.SDK_INT;
        return api;
    }

    //app信息管理
    public static List<ModelBean> getAppinfo(Context context){
        List<ModelBean> modelBeen = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> installedApplications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (int i=0; i<installedApplications.size();i++){
            ModelBean modelBean = new ModelBean();
            ApplicationInfo applicationInfo = installedApplications.get(i);
            String packname = applicationInfo.packageName;
            modelBean.setSoftma_tv_pack(packname); //设置包名
            Drawable drawable = applicationInfo.loadIcon(pm);
            modelBean.setSoftma_img(drawable);//设置图片
            String name = applicationInfo.loadLabel(pm).toString();
            modelBean.setSoftma_tv_name(name);
            try {
                PackageInfo packageInfo = pm.getPackageInfo(packname, 0);
                String version = packageInfo.versionName;
                modelBean.setSoftma_tv_version(version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            int use = isUserApp(applicationInfo);
            if (use == 1){
                modelBean.setSoftma_isuse(true);
            }else {
                modelBean.setSoftma_isuse(false);
            }
            modelBeen.add(modelBean);
        }
        return modelBeen;
    }

    //获取手机cpu的名称
    public static String getCpuName(){
        String str1 = "/proc/cpuinfo";
        String str2 ;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            str2 = localBufferedReader.readLine();
            String[] split = str2.split("\\s+",2);
            return split[1];
        } catch (IOException e) {

        }
        return null;
    }
    //获得cpu的数量
    public static int getCpuNumber(){
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File file) {
                if (Pattern.matches("cpu[0-9]]",file.getName())){
                    return true;
                }
                return false;
            }
        }
        try {
            File f = new File("/sys/devices/system/cpu/");
            File[] listFiles = f.listFiles(new CpuFilter());
            return listFiles.length;
        } catch (Exception e) {
            return 1;
        }
    }
    //获得相机分辨率
    public static String getCameraPower(){
            android.hardware.Camera camera = android.hardware.Camera.open();
            android.hardware.Camera.Parameters parameters = camera.getParameters();
            List<android.hardware.Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
            android.hardware.Camera.Size size = supportedPictureSizes.get(0);
            for (int i = 0; i < supportedPictureSizes.size(); i++) {
                android.hardware.Camera.Size s = supportedPictureSizes.get(i);
                if (size.width * size.height < s.width * s.width) {
                    size = s;
                }
            }
            return size.width + "*" + size.height;
    }
    //是否root
    public static boolean getRoot(){
        File f = new File("/system/bin/su");
        File l = new File("/system/xbin/su");
        if (f.exists()&&l.exists()){
            return true;
        }
        return false;
    }
}
