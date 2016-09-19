package com.example.administrator.phonemanager.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Yao on 2016/9/13.
 */
public class PhoneCleanBean {
    public Boolean getIsselect() {
        return isselect;
    }

    public void setIsselect(Boolean isselect) {
        this.isselect = isselect;
    }

    private Boolean isselect;
    private int id;
    private String name;
    private String packname;
    private String filePath;
    private Drawable drawable;
    private long filesize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }


}
