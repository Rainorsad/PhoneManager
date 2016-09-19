package com.example.administrator.phonemanager.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ModelBean {
    public static final int X_type = 1; //显示用户进程
    public static final int Y_type = 2; //显示系统进程
    public static final int Z_type = 3; //显示软件管理界面
    public static final int A_type = 4; //显示联系人界面
    public static final int B_type = 5; //显示文件详情界面

    public static int getY_type() {
        return Y_type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    //手机加速界面线程显示
    private int pid; //进程唯一标识，可以根据pid结束进程
    private String imgcheck;
    private Drawable imagename;  //图标
    private String threadname;  //名称
    private long threadsql; //内存
    private Boolean isselect = false; //是否选中
    private String packname; //包名，APP的唯一标识

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public Boolean getIsselect() {
        return isselect;
    }

    public void setIsselect(Boolean isselect) {
        this.isselect = isselect;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Drawable getImagename() {
        return imagename;
    }

    public void setImagename(Drawable imagename) {
        this.imagename = imagename;
    }

    public String getImgcheck() {
        return imgcheck;
    }

    public void setImgcheck(String imgcheck) {
        this.imgcheck = imgcheck;
    }

    public long getThreadsql() {
        return threadsql;
    }

    public void setThreadsql(long threadsql) {
        this.threadsql = threadsql;
    }

    public static int getX_type() {
        return X_type;
    }

    public String getThreadname() {
        return threadname;

    }

    public void setThreadname(String threadname) {
        this.threadname = threadname;
    }

    //软件管理界面
    private Drawable softma_img; //图片
    private String softma_tv_name; //app名字
    private String softma_tv_pack; //包名
    private String softma_tv_version; //版本号
    private boolean softma_isuse; //是否为用户软件

    public static int getZ_type() {
        return Z_type;
    }

    public Drawable getSoftma_img() {
        return softma_img;
    }

    public void setSoftma_img(Drawable softma_img) {
        this.softma_img = softma_img;
    }

    public String getSoftma_tv_name() {
        return softma_tv_name;
    }

    public void setSoftma_tv_name(String softma_tv_name) {
        this.softma_tv_name = softma_tv_name;
    }

    public String getSoftma_tv_version() {
        return softma_tv_version;
    }

    public void setSoftma_tv_version(String softma_tv_version) {
        this.softma_tv_version = softma_tv_version;
    }

    public String getSoftma_tv_pack() {
        return softma_tv_pack;
    }

    public void setSoftma_tv_pack(String softma_tv_pack) {
        this.softma_tv_pack = softma_tv_pack;
    }

    public boolean isSoftma_isuse() {
        return softma_isuse;
    }

    public void setSoftma_isuse(boolean softma_isuse) {
        this.softma_isuse = softma_isuse;
    }

    //显示联系人界面信息
    private String contacts_id; //联系人id
    private Drawable contacts_phoneimg; //联系人图片
    private Drawable contacts_set; //联系人设置图片
    private String contacts_name; //联系人姓名

    public static int getA_type() {
        return A_type;
    }

    public String getContacts_id() {
        return contacts_id;
    }

    public void setContacts_id(String contacts_id) {
        this.contacts_id = contacts_id;
    }

    public Drawable getContacts_phoneimg() {
        return contacts_phoneimg;
    }

    public void setContacts_phoneimg(Drawable contacts_phoneimg) {
        this.contacts_phoneimg = contacts_phoneimg;
    }

    public Drawable getContacts_set() {
        return contacts_set;
    }

    public void setContacts_set(Drawable contacts_set) {
        this.contacts_set = contacts_set;
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public String getContacts_phonenumber() {
        return contacts_phonenumber;
    }

    public void setContacts_phonenumber(String contacts_phonenumber) {
        this.contacts_phonenumber = contacts_phonenumber;
    }

    private String contacts_phonenumber; //联系人电话号码


    //文件信息显示界面
    private Boolean filemanagrshow_isselect = false; //是否选中
    private String filemanagershow_name; // 文件名
    private String filemanager_path;//文件绝对路径
    private String filemanagershow_tim; //文件创建时间
    private long filemanager_memory; //文件内存
    private Drawable filemanager_img; //文件图标
    private Bitmap filemanager_bit;//bit型文件图标
    private File filemanager_file;//文件

    public File getFilemanager_file() {
        return filemanager_file;
    }

    public void setFilemanager_file(File filemanager_file) {
        this.filemanager_file = filemanager_file;
    }

    public String getFilemanager_path() {
        return filemanager_path;
    }

    public void setFilemanager_path(String filemanager_path) {
        this.filemanager_path = filemanager_path;
    }

    public Bitmap getFilemanager_bit() {
        return filemanager_bit;
    }

    public void setFilemanager_bit(Bitmap filemanager_bit) {
        this.filemanager_bit = filemanager_bit;
    }

    public static int getB_type() {
        return B_type;
    }

    public Boolean getFilemanagrshow_isselect() {
        return filemanagrshow_isselect;
    }

    public void setFilemanagrshow_isselect(Boolean filemanagrshow_isselect) {
        this.filemanagrshow_isselect = filemanagrshow_isselect;
    }

    public String getFilemanagershow_name() {
        return filemanagershow_name;
    }

    public void setFilemanagershow_name(String filemanagershow_name) {
        this.filemanagershow_name = filemanagershow_name;
    }

    public String getFilemanagershow_tim() {
        return filemanagershow_tim;
    }

    public void setFilemanagershow_tim(String filemanagershow_tim) {
        this.filemanagershow_tim = filemanagershow_tim;
    }

    public long getFilemanager_memory() {
        return filemanager_memory;
    }

    public void setFilemanager_memory(long filemanager_memory) {
        this.filemanager_memory = filemanager_memory;
    }

    public Drawable getFilemanager_img() {
        return filemanager_img;
    }

    public void setFilemanager_img(Drawable filemanager_img) {
        this.filemanager_img = filemanager_img;
    }


}
