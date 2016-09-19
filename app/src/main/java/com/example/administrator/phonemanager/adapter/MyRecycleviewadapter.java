package com.example.administrator.phonemanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.bean.ModelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyRecycleviewadapter extends RecyclerView.Adapter{

    private Context context;
    private List<ModelBean> modelBeans;
    private MyRecycleviewadapter adapter;
    private LruCache<String,Bitmap> lru;
    public List<ModelBean> delemodelBeans;
    public MyRecycleviewadapter(Context context,List<ModelBean> modelBeans){
        this.context = context;
        this.modelBeans = modelBeans;
        adapter = this;
        lru = new LruCache<String,Bitmap>(1024*10);
        delemodelBeans = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case ModelBean.X_type:
                v = LayoutInflater.from(context).inflate(R.layout.item_phonesteep_thread,null);
                viewHolder = new ThreadShowHolder(v);
                break;
            case ModelBean.Y_type:
                v = LayoutInflater.from(context).inflate(R.layout.item_phonesteep_thread,null);
                viewHolder = new ThreadShowHolder(v);
                break;
            case ModelBean.Z_type:
                v = LayoutInflater.from(context).inflate(R.layout.item_softmanager_thread,null);
                viewHolder = new SoftManagerViewHolder(v);
                break;
            case ModelBean.A_type:
                v = LayoutInflater.from(context).inflate(R.layout.item_contacts,null);
                viewHolder = new ContactsManagerViewHolder(v);
                break;
            case ModelBean.B_type:
                v = LayoutInflater.from(context).inflate(R.layout.item_fieshow,null);
                viewHolder = new FileManagerShowViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ModelBean.X_type:
                ThreadShowHolder threadShowHolder = (ThreadShowHolder) holder;
                if (modelBeans.get(position).getIsselect()){
                    threadShowHolder.imgcheck.setImageResource(R.mipmap.radio_select_yes);
                }else {
                    threadShowHolder.imgcheck.setImageResource(R.mipmap.radio_select_no);
                }
                threadShowHolder.imgname.setImageDrawable(modelBeans.get(position).getImagename());
                threadShowHolder.tvname.setText(modelBeans.get(position).getThreadname());
                threadShowHolder.tvsql.setText("内存: "+ android.text.format.Formatter.formatFileSize(context,modelBeans.get(position).getThreadsql()));
                threadShowHolder.imgcheck.setOnClickListener(new MyOnclick(position));
                break;
            case ModelBean.Y_type:
                ThreadShowHolder threadShowHolder1 = (ThreadShowHolder) holder;
                threadShowHolder1.imgcheck.setImageResource(R.mipmap.radio_select_no);
                threadShowHolder1.imgname.setImageDrawable(modelBeans.get(position).getImagename());
                threadShowHolder1.tvname.setText(modelBeans.get(position).getThreadname());
                threadShowHolder1.tvsql.setText("内存: "+ android.text.format.Formatter.formatFileSize(context,modelBeans.get(position).getThreadsql()));
                break;
            case ModelBean.Z_type:
                SoftManagerViewHolder softManagerViewHolder = (SoftManagerViewHolder) holder;
                softManagerViewHolder.img.setImageDrawable(modelBeans.get(position).getSoftma_img());
                softManagerViewHolder.tv_name.setText(modelBeans.get(position).getSoftma_tv_name());

                if (modelBeans.get(position).getSoftma_tv_pack().length()>=15){
                    softManagerViewHolder.tv_pack.setText(modelBeans.get(position).getSoftma_tv_pack().subSequence(0,15)+"...");
                }else {
                    softManagerViewHolder.tv_pack.setText(modelBeans.get(position).getSoftma_tv_pack());
                }
                if (modelBeans.get(position).getSoftma_tv_version().length()>=10){
                    softManagerViewHolder.tv_version.setText(modelBeans.get(position).getSoftma_tv_version().subSequence(0,10)+"...");
                }else {
                    softManagerViewHolder.tv_version.setText(modelBeans.get(position).getSoftma_tv_version());
                }
                break;
            case ModelBean.A_type:
                ContactsManagerViewHolder contactsManagerViewHolder = (ContactsManagerViewHolder) holder;
                contactsManagerViewHolder.img_phone.setImageResource(R.drawable.phone_email);
                contactsManagerViewHolder.img_set.setImageResource(R.drawable.edit_email);
                contactsManagerViewHolder.tv_name.setText(modelBeans.get(position).getContacts_name());
                contactsManagerViewHolder.tv_phone.setText(modelBeans.get(position).getContacts_phonenumber());

                contactsManagerViewHolder.img_set.setOnClickListener(new ContastOnclick(position));
                break;
            case ModelBean.B_type:
                FileManagerShowViewHolder fileManagerShowViewHolder = (FileManagerShowViewHolder) holder;
                if (modelBeans.get(position).getFilemanagrshow_isselect()){
                    fileManagerShowViewHolder.img_select.setImageResource(R.mipmap.radio_select_yes);
//                    Glide.with(context).load(R.mipmap.radio_select_yes).into(fileManagerShowViewHolder.img_select);
                }else {
                    fileManagerShowViewHolder.img_select.setImageResource(R.mipmap.radio_select_no);
//                    Glide.with(context).load(R.mipmap.radio_select_no).into(fileManagerShowViewHolder.img_select);
                }
                if (modelBeans.get(position).getFilemanager_img() == null){
                    Bitmap bitmap = lru.get(modelBeans.get(position).getFilemanager_path());
                    Glide.with(context).load(bitmap).thumbnail(0.001f).into(fileManagerShowViewHolder.img_name);
//                    if (bitmap!=null){
//                        fileManagerShowViewHolder.img_name.setImageBitmap(bitmap);
//                    }else {
//                        fileManagerShowViewHolder.img_name.setImageBitmap(modelBeans.get(position).getFilemanager_bit());
//                        lru.put(modelBeans.get(position).getFilemanager_path(),modelBeans.get(position).getFilemanager_bit());//缓存
//                    }
                } else {
                    Glide.with(context).load("").placeholder(modelBeans.get(position).getFilemanager_img()).thumbnail(0.001f).into(fileManagerShowViewHolder.img_name);
                }
                if (modelBeans.get(position).getFilemanagershow_name().length()>=15) {
                    fileManagerShowViewHolder.tv_name.setText(modelBeans.get(position).getFilemanagershow_name().subSequence(0, 15) + "...");
                }else {
                    fileManagerShowViewHolder.tv_name.setText(modelBeans.get(position).getFilemanagershow_name());
                }
                fileManagerShowViewHolder.tv_time.setText(modelBeans.get(position).getFilemanagershow_tim());
                fileManagerShowViewHolder.tv_memory.setText("内存： "+android.text.format.Formatter.formatFileSize(context,modelBeans.get(position).getFilemanager_memory()));

                fileManagerShowViewHolder.img_select.setOnClickListener(new FileManagerShowOnClick(position));
        }

    }

    @Override
    public int getItemCount() {
        return modelBeans.size();
    }

    //手机加速界面显示
    public class ThreadShowHolder extends RecyclerView.ViewHolder {
        private ImageView imgcheck, imgname;
        private TextView tvname, tvsql;

        public ThreadShowHolder(View itemView) {
            super(itemView);
            imgcheck = (ImageView) itemView.findViewById(R.id.item_phsteep_thread_imcheck);
            imgname = (ImageView) itemView.findViewById(R.id.item_phsteep_thread_imname);
            tvname = (TextView) itemView.findViewById(R.id.item_phsteep_tvname);
            tvsql = (TextView) itemView.findViewById(R.id.item_phsteep_tvsql);

        }
    }

    //软件管理界面
    public class SoftManagerViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_pack,tv_version;
        private ImageView img;
        public SoftManagerViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_softman_imname);
            tv_name = (TextView) itemView.findViewById(R.id.item_softman_tvname);
            tv_pack = (TextView) itemView.findViewById(R.id.item_softman_tvpack);
            tv_version = (TextView) itemView.findViewById(R.id.item_softman_tvver);
        }
    }

    //联系人管理界面
    public class ContactsManagerViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_phone;
        private ImageView img_phone,img_set;
        public ContactsManagerViewHolder(View itemView) {
            super(itemView);
            img_phone = (ImageView) itemView.findViewById(R.id.item_contacts_imgphone);
            img_set = (ImageView) itemView.findViewById(R.id.item_contacts_imgset);
            tv_name = (TextView) itemView.findViewById(R.id.item_contacts_tvname);
            tv_phone = (TextView) itemView.findViewById(R.id.item_contacts_tvphone);
        }
    }

    //文件显示界面viewholder
    public class FileManagerShowViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_time,tv_memory;
        private ImageView img_select,img_name;
        public FileManagerShowViewHolder(View itemView) {
            super(itemView);
            tv_memory = (TextView) itemView.findViewById(R.id.item_fieshow_tvmemory);
            tv_time = (TextView) itemView.findViewById(R.id.item_fieshow_tvtime);
            tv_name = (TextView) itemView.findViewById(R.id.item_fieshow_tvname);
            img_name = (ImageView) itemView.findViewById(R.id.item_fieshow_imgname);
            img_select = (ImageView) itemView.findViewById(R.id.item_fieshow_imgselect);
        }
    }

    public int getItemViewType(int position){
        return modelBeans.get(position).getType();
    }

    /**
     * 加速界面点击监听事件
     */
    class MyOnclick implements View.OnClickListener{
        int index;
        public MyOnclick(int index){
            this.index = index;
        }
        @Override
        public void onClick(View view) {
            ModelBean m = modelBeans.get(index);
            if (m.getIsselect()){
                m.setIsselect(false);
            }else {
                m.setIsselect(true);
            }
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 通讯录界面编辑按钮点击事件
     */
    class ContastOnclick implements View.OnClickListener{
        int index;
        public ContastOnclick(int index){
            this.index = index;
        }
        @Override
        public void onClick(View view) {
            ModelBean m = modelBeans.get(index);

        }
    }

    /**
     * 文件详细信息显示界面点击事件
     */
    class FileManagerShowOnClick implements View.OnClickListener{
        int anInt;
        public FileManagerShowOnClick(int anInt){
            this.anInt = anInt;
        }
        @Override
        public void onClick(View view) {
            ModelBean m = modelBeans.get(anInt);
            if (m.getFilemanagrshow_isselect()){
                m.setFilemanagrshow_isselect(false);
                boolean contains = delemodelBeans.contains(m);
                if (contains){
                    delemodelBeans.remove(m);
                }
            }else {
                m.setFilemanagrshow_isselect(true);
                delemodelBeans.add(m);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
