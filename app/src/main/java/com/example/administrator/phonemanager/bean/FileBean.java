package com.example.administrator.phonemanager.bean;

import java.io.File;

/**
 * Created by Administrator on 2016/9/7.
 */
public class FileBean {
    private File file;
    private String filename;
    private String filetype;
    private String fileIoc;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFileIoc() {
        return fileIoc;
    }

    public void setFileIoc(String fileIoc) {
        this.fileIoc = fileIoc;
    }
}
