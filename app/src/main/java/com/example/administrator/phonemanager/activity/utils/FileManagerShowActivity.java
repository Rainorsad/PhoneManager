package com.example.administrator.phonemanager.activity.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;
import com.example.administrator.phonemanager.adapter.MyRecycleviewadapter;
import com.example.administrator.phonemanager.bean.FileBean;
import com.example.administrator.phonemanager.bean.ModelBean;
import com.example.administrator.phonemanager.utils.FileManager;
import com.example.administrator.phonemanager.utils.MyRecycleViewItemDecoration;
import com.example.administrator.phonemanager.utils.PhoneUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yao on 2016/9/9.
 */
public class FileManagerShowActivity extends BaseActivity {
    private TextView tv_head,tv_number,tv_memory;
    private ImageView img_head_left,img_head_right;
    private Button bt;
    private RecyclerView recyclerView;
    private List<FileBean> fileBeans;
    private List<ModelBean> modelBeans;
    private FileBean fileBean;
    private ModelBean modelBean;
    private long infoSize = 0;
    private SimpleDateFormat format;
    private FileManager fm;
    private MyRecycleviewadapter myRecycleviewadapter;
    private String type;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_filemanagershow);
    }

    @Override
    protected void setView() {
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        bt = (Button) findViewById(R.id.item_filemanager_bt);
        tv_memory = (TextView) findViewById(R.id.item_filemanager_tvmemery);
        tv_number = (TextView) findViewById(R.id.item_filemanager_tvnumber);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MyRecycleViewItemDecoration m =new MyRecycleViewItemDecoration(MyRecycleViewItemDecoration.VERTICAL);
        m.setColor(0xFFFF0000);
        m.setSize(2);
        recyclerView.addItemDecoration(m);

        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }

    @Override
    protected void setDeal() {
        type = getIntent().getStringExtra("type");
        initHead(type);
        initMainData();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ModelBean> delemodelBeans = myRecycleviewadapter.delemodelBeans;
                for(int i=0;i<delemodelBeans.size();i++){
                    ModelBean modelBean = delemodelBeans.get(i);
                    File filemanager_file = modelBean.getFilemanager_file();

                    FileBean fileBean = fileBeans.get(i);
                    if (type.equals(FileManager.TYPR_ANY)){
                        fm.fileBeans.remove(fileBean);
                        fm.fileSize = fm.fileSize - modelBean.getFilemanager_path().length();
                        infoSize = fm.fileSize - modelBean.getFilemanager_path().length();
                    }else if (type.equals(FileManager.TYPE_TXT)){
                        fm.filetext.remove(fileBean);
                        fm.fileBeans.remove(fileBean);
                        fm.filetextsize = fm.filetextsize - modelBean.getFilemanager_path().length();
                        infoSize = fm.filetextsize - modelBean.getFilemanager_path().length();
                    }else if (type.equals(FileManager.TYPE_VIDEO)){
                        fm.fileVideo.remove(fileBean);
                        fm.fileBeans.remove(fileBean);
                        fm.filevideosize = fm.filevideosize - modelBean.getFilemanager_path().length();
                        infoSize = fm.filevideosize - modelBean.getFilemanager_path().length();
                    }else if (type.equals(FileManager.TYPE_AUDIO)){
                        fm.fileaudeo.remove(fileBean);
                        fm.fileBeans.remove(fileBean);
                        fm.fileaudeosize = fm.fileaudeosize - modelBean.getFilemanager_path().length();
                        infoSize = fm.fileaudeosize - modelBean.getFilemanager_path().length();
                    }else if (type.equals(FileManager.TYPE_IMAGE)){
                        fm.fileImage.remove(fileBean);
                        fm.fileBeans.remove(fileBean);
                        fm.fileImageSize = fm.fileImageSize - modelBean.getFilemanager_path().length();
                        infoSize = fm.fileImageSize - modelBean.getFilemanager_path().length();
                    }else if (type.equals(FileManager.TYPE_ZIP)){
                        fm.filezip.remove(fileBean);
                        fm.fileBeans.remove(fileBean);
                        fm.filezipsize = fm.filezipsize - modelBean.getFilemanager_path().length();
                        infoSize = fm.filezipsize - modelBean.getFilemanager_path().length();
                    }else if (type.equals(FileManager.TYPE_APK)){
                        fm.fileapk.remove(fileBean);
                        fm.fileBeans.remove(fileBean);
                        fm.fileapksize = fm.fileapksize - modelBean.getFilemanager_path().length();
                        infoSize = fm.fileapksize - modelBean.getFilemanager_path().length();
                    }

                    if (filemanager_file.exists()){
                        filemanager_file.delete();
                    }
                    modelBeans.remove(modelBean);
                }
                myRecycleviewadapter.notifyDataSetChanged();
                tv_number.setText(modelBeans.size()+"项");
                tv_memory.setText(Formatter.formatFileSize(FileManagerShowActivity.this,infoSize));
            }
        });
    }

    private void initMainData() {
        modelBeans = new ArrayList<>();
        for(int i=0; i<fileBeans.size();i++) {
            modelBean = new ModelBean();
            fileBean = fileBeans.get(i);
            Resources resources = this.getResources();
            File f = fileBean.getFile();
            modelBean.setFilemanager_file(f);
            modelBean.setFilemanager_path(f.getAbsolutePath());
            if (fileBean.getFiletype().equals(FileManager.TYPE_IMAGE)) {
                Bitmap bitmap = PhoneUtils.loadBitmap(f.getAbsolutePath(), 20, 20, this);
                modelBean.setFilemanager_bit(bitmap);
            }else {
                int resid = resources.getIdentifier(fileBean.getFileIoc(), "drawable", this.getPackageName());
                if (resid <= 0) {
                    resid = R.mipmap.ic_launcher;
                }
                Drawable dra = resources.getDrawable(resid);
                modelBean.setFilemanager_img(dra);

            }
            String name = f.getName();
            modelBean.setFilemanagershow_name(name);
            long time = f.lastModified();
            modelBean.setFilemanagershow_tim(format.format(time));
            modelBean.setFilemanagrshow_isselect(false);
            modelBean.setType(ModelBean.B_type);
            modelBean.setFilemanager_memory(f.length());
            modelBeans.add(modelBean);
        }
        myRecycleviewadapter = new MyRecycleviewadapter(FileManagerShowActivity.this,modelBeans);
        recyclerView.setAdapter(myRecycleviewadapter);
    }

    /**
     * 处理头部数据
     * @param type
     */
    private void initHead(final String type) {
        fm = FileManager.getFileManager();
        img_head_right.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        img_head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.putExtra("type",type);
                setResult(200,it);
                finish();
            }
        });
        if (type.equals(FileManager.TYPR_ANY)){
            tv_head.setText("全部软件信息");
            fileBeans = fm.fileBeans;
            infoSize = fm.fileSize;
        }else if (type.equals(FileManager.TYPE_TXT)){
            tv_head.setText("文件信息");
            fileBeans = fm.filetext;
            infoSize = fm.filetextsize;
        }else if (type.equals(FileManager.TYPE_VIDEO)){
            tv_head.setText("视频信息");
            fileBeans = fm.fileVideo;
            infoSize = fm.filevideosize;
        }else if (type.equals(FileManager.TYPE_AUDIO)){
            tv_head.setText("语音信息");
            fileBeans = fm.fileaudeo;
            infoSize = fm.fileaudeosize;
        }else if (type.equals(FileManager.TYPE_IMAGE)){
            tv_head.setText("图片信息");
            fileBeans = fm.fileImage;
            infoSize = fm.fileImageSize;
        }else if (type.equals(FileManager.TYPE_ZIP)){
            tv_head.setText("压缩包信息");
            fileBeans = fm.filezip;
            infoSize = fm.filezipsize;
        }else if (type.equals(FileManager.TYPE_APK)){
            tv_head.setText("安装包信息");
            fileBeans = fm.fileapk;
            infoSize = fm.fileapksize;
        }

        tv_number.setText(fileBeans.size()+"项");
        tv_memory.setText(Formatter.formatFileSize(this,infoSize));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4){
            Intent it = new Intent();
            it.putExtra("type",type);
            setResult(200,it);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
