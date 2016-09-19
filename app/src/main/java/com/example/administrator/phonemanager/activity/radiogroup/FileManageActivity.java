package com.example.administrator.phonemanager.activity.radiogroup;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;
import com.example.administrator.phonemanager.activity.utils.FileManagerShowActivity;
import com.example.administrator.phonemanager.utils.FileManager;

/**
 * Created by Administrator on 2016/9/7.
 */
public class FileManageActivity extends BaseActivity {
    private ImageView img_all_img,img_flie_img,img_video_img,img_audio_img,img_img_img,img_zip_img,img_jar_img,img_head_left,img_head_right;
    private TextView tv_head,tv_all_memory,tv_all_number,tv_file_memory,tv_file_number,tv_video_memory,tv_video_number,tv_audio_memory, tv_audio_number,
    tv_img_memory,tv_img_number,tv_zip_memory,tv_zip_number,tv_jar_memory,tv_jar_number,tv_user_all;
    private LinearLayout lin_all,lin_txt,lin_video,lin_audio,lin_img,lin_zip,lin_jar;
    private ProgressBar pro_all,pro_file,pro_img,pro_video,pro_audio,pro_zip,pro_jar;
    private FileManager fil;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_filemanager);
    }

    @Override
    protected void setView() {

        lin_all = (LinearLayout) findViewById(R.id.lin_all); lin_all.setOnClickListener(l);
        lin_txt = (LinearLayout) findViewById(R.id.lin_txt); lin_txt.setOnClickListener(l);
        lin_video = (LinearLayout) findViewById(R.id.lin_video); lin_video.setOnClickListener(l);
        lin_audio = (LinearLayout) findViewById(R.id.lin_audio); lin_audio.setOnClickListener(l);
        lin_img = (LinearLayout) findViewById(R.id.lin_img); lin_img.setOnClickListener(l);
        lin_zip = (LinearLayout) findViewById(R.id.lin_zip); lin_zip.setOnClickListener(l);
        lin_jar = (LinearLayout) findViewById(R.id.lin_jar); lin_jar.setOnClickListener(l);

        img_all_img = (ImageView) findViewById(R.id.filemanager_all_img); img_all_img.setOnClickListener(l);
        img_flie_img = (ImageView) findViewById(R.id.filemanager_file_img); img_all_img.setOnClickListener(l);
        img_video_img = (ImageView) findViewById(R.id.filemanager_video_img); img_all_img.setOnClickListener(l);
        img_audio_img = (ImageView) findViewById(R.id.filemanager_audio_img); img_all_img.setOnClickListener(l);
        img_img_img = (ImageView) findViewById(R.id.filemanager_img_img); img_all_img.setOnClickListener(l);
        img_zip_img = (ImageView) findViewById(R.id.filemanager_zip_img); img_all_img.setOnClickListener(l);
        img_jar_img = (ImageView) findViewById(R.id.filemanager_jar_img); img_all_img.setOnClickListener(l);
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        tv_head = (TextView) findViewById(R.id.head_layout_text);

        pro_all = (ProgressBar) findViewById(R.id.filemanager_all_pro);
        pro_file = (ProgressBar) findViewById(R.id.filemanager_file_pro);
        pro_video = (ProgressBar) findViewById(R.id.filemanager_video_pro);
        pro_audio = (ProgressBar) findViewById(R.id.filemanager_audio_pro);
        pro_img = (ProgressBar) findViewById(R.id.filemanager_img_pro);
        pro_zip = (ProgressBar) findViewById(R.id.filemanager_zip_pro);
        pro_jar = (ProgressBar) findViewById(R.id.filemanager_jar_pro);

        tv_all_memory = (TextView) findViewById(R.id.filemanager_all_tvmemory);
        tv_all_number = (TextView) findViewById(R.id.filemanager_all_tvfile);
        tv_file_memory = (TextView) findViewById(R.id.filemanager_file_tvmemory);
        tv_file_number = (TextView) findViewById(R.id.filemanager_file_tvfile);
        tv_video_memory = (TextView) findViewById(R.id.filemanager_video_tvmemory);
        tv_video_number = (TextView) findViewById(R.id.filemanager_video_tvfile);
        tv_audio_memory = (TextView) findViewById(R.id.filemanager_audio_tvmemory);
        tv_audio_number = (TextView) findViewById(R.id.filemanager_audio_tvfile);
        tv_img_memory = (TextView) findViewById(R.id.filemanager_img_tvmemory);
        tv_img_number = (TextView) findViewById(R.id.filemanager_img_tvfile);
        tv_zip_memory = (TextView) findViewById(R.id.filemanager_zip_tvmemory);
        tv_zip_number = (TextView) findViewById(R.id.filemanager_zip_tvfile);
        tv_jar_memory = (TextView) findViewById(R.id.filemanager_jar_tvmemory);
        tv_jar_number = (TextView) findViewById(R.id.filemanager_jar_tvfile);
        tv_user_all = (TextView) findViewById(R.id.filemanager_showall);
    }

    @Override
    protected void setDeal() {
        fil = FileManager.getFileManager();
        fil.setMHandler(handler);
        initHead();
        inxitMainData();
        startSelect();
    }

    private void startSelect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                fil.fileBeans.clear();
                fil.fileSize = 0;
                fil.filetext.clear();
                fil.filetextsize = 0;
                fil.fileVideo.clear();
                fil.filevideosize = 0;
                fil.fileaudeo.clear();
                fil.fileaudeosize = 0;
                fil.fileImage.clear();
                fil.fileImageSize = 0;
                fil.filezip.clear();
                fil.filezipsize = 0;
                fil.fileapk.clear();
                fil.fileapksize = 0;
                String inPath = FileManager.inPath;
                String outPath = FileManager.outPath;
                if (inPath!=null&&outPath!=null){
                    fil.seleFile(inPath,false);
                    fil.seleFile(outPath,true);
                }else if (inPath!= null&& outPath==null){
                    fil.seleFile(inPath,true);
                }else if (outPath!=null&&inPath==null){
                    fil.seleFile(outPath,true);
                }
            }
        }).start();

    }

    /**
     * 处理主要数据
     */
    private void inxitMainData() {

    }

    /**
     * 处理头部布局
     */
    private void initHead() {
        img_head_right.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        tv_head.setText("软件管理");
        img_head_left.setOnClickListener(l);

    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String type = (String) msg.obj;
                    if (type.equals(FileManager.TYPE_TXT)){
                        tv_file_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.filetextsize));
                        tv_file_number.setText(fil.filetext.size()+"项");
                    }else if (type.equals(FileManager.TYPE_VIDEO)){
                    tv_video_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.filevideosize));
                    tv_video_number.setText(fil.fileVideo.size()+"项");
                    }else if (type.equals(FileManager.TYPE_AUDIO)){
                        tv_audio_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileaudeosize));
                        tv_audio_number.setText(fil.fileaudeo.size()+"项");
                    }else if (type.equals(FileManager.TYPE_IMAGE)){
                        tv_img_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileImageSize));
                        tv_img_number.setText(fil.fileImage.size()+"项");
                    }else if (type.equals(FileManager.TYPE_ZIP)){
                        tv_zip_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.filezipsize));
                        tv_zip_number.setText(fil.filezip.size()+"项");
                    }else if (type.equals(FileManager.TYPE_APK)){
                        tv_jar_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileapksize));
                        tv_jar_number.setText(fil.fileapk.size()+"项");
                    }
                    tv_all_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileSize));
                    tv_all_number.setText(fil.fileBeans.size()+"项");
                    tv_user_all.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileSize));
                    break;
                case 2:
                    pro_file.setVisibility(View.GONE);
                    img_flie_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_flie_img);

                    pro_video.setVisibility(View.GONE);
                    img_video_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_video_img);

                    pro_audio.setVisibility(View.GONE);
                    img_audio_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_audio_img);

                    pro_img.setVisibility(View.GONE);
                    img_img_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_img_img);

                    pro_zip.setVisibility(View.GONE);
                    img_zip_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_zip_img);

                    pro_jar.setVisibility(View.GONE);
                    img_jar_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_jar_img);

                    pro_all.setVisibility(View.GONE);
                    img_all_img.setVisibility(View.VISIBLE);
                    Glide.with(FileManageActivity.this).load(R.mipmap.back_002).into(img_all_img);
                    break;
            }
        }
    };

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.filemanager_all_img || view.getId() == R.id.lin_all) {
                jump(FileManager.TYPR_ANY);
            } else if (view.getId() == R.id.filemanager_file_img || view.getId() == R.id.lin_txt) {
                jump(FileManager.TYPE_TXT);
            } else if (view.getId() == R.id.filemanager_video_img || view.getId() == R.id.lin_video) {
                jump(FileManager.TYPE_VIDEO);
            } else if (view.getId() == R.id.filemanager_audio_img || view.getId() == R.id.lin_audio) {
                jump(FileManager.TYPE_AUDIO);
            } else if (view.getId() == R.id.filemanager_img_img || view.getId() == R.id.lin_img) {
                jump(FileManager.TYPE_IMAGE);
            } else if (view.getId() == R.id.filemanager_zip_img || view.getId() == R.id.lin_zip) {
                jump(FileManager.TYPE_ZIP);
            } else if (view.getId() == R.id.filemanager_jar_img || view.getId() == R.id.lin_jar) {
                jump(FileManager.TYPE_APK);
            }else if (view.getId() == R.id.head_layout_image_left){
                finish();
            }
        }
    };


    /**
     * 界面跳转
     */
    private void jump(String type) {
        if (fil.isstop == false) {
            ToaL(FileManageActivity.this, "正在加载...");
            return;
        }
        Intent it = new Intent(FileManageActivity.this, FileManagerShowActivity.class);
        it.putExtra("type", type);
        startActivityForResult(it,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200){
            String type = data.getStringExtra("type");
            if (type.equals(FileManager.TYPE_TXT)){
                tv_file_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.filetextsize));
                tv_file_number.setText(fil.filetext.size()+"项");
            }else if (type.equals(FileManager.TYPE_VIDEO)){
                tv_video_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.filevideosize));
                tv_video_number.setText(fil.fileVideo.size()+"项");
            }else if (type.equals(FileManager.TYPE_AUDIO)){
                tv_audio_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileaudeosize));
                tv_audio_number.setText(fil.fileaudeo.size()+"项");
            }else if (type.equals(FileManager.TYPE_IMAGE)){
                tv_img_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileImageSize));
                tv_img_number.setText(fil.fileImage.size()+"项");
            }else if (type.equals(FileManager.TYPE_ZIP)){
                tv_zip_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.filezipsize));
                tv_zip_number.setText(fil.filezip.size()+"项");
            }else if (type.equals(FileManager.TYPE_APK)){
                tv_jar_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileapksize));
                tv_jar_number.setText(fil.fileapk.size()+"项");
            }
            tv_all_memory.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileSize));
            tv_all_number.setText(fil.fileBeans.size()+"项");
            tv_user_all.setText(Formatter.formatFileSize(FileManageActivity.this,fil.fileSize));
        }
    }
}
