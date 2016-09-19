package com.example.administrator.phonemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.bean.PhoneCleanBean;

import java.util.List;

/**
 * Created by Yao on 2016/9/13.
 */
public class MyrecycleViewAdapterTwo extends RecyclerView.Adapter{
    private Context context;
    public List<PhoneCleanBean> fileBeen;
    private MyrecycleViewAdapterTwo adapter;
    public MyrecycleViewAdapterTwo(Context context, List<PhoneCleanBean> fileBeen){
        this.context = context;
        this.fileBeen = fileBeen;
        adapter = this;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_fieshow,null);
        RecyclerView.ViewHolder viewHolder = new PhoneCleanHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhoneCleanHolder phoneCleanHolder = (PhoneCleanHolder) holder;
        PhoneCleanBean phoneCleanBean = fileBeen.get(position);
        if (phoneCleanBean.getIsselect()){
            phoneCleanHolder.img_select.setImageResource(R.mipmap.radio_select_yes);
        }else {
            phoneCleanHolder.img_select.setImageResource(R.mipmap.radio_select_no);
        }
        Glide.with(context).load("").placeholder(phoneCleanBean.getDrawable()).into(phoneCleanHolder.img_name);
        phoneCleanHolder.tv_name.setText(phoneCleanBean.getName());
        if (phoneCleanBean.getFilePath().length()>=15){
            phoneCleanHolder.tv_time.setText(phoneCleanBean.getFilePath().subSequence(0, 15) + "...");
        }else {
            phoneCleanHolder.tv_time.setText(phoneCleanBean.getFilePath());
        }
        phoneCleanHolder.tv_memory.setText(Formatter.formatFileSize(context,phoneCleanBean.getFilesize()));

        phoneCleanHolder.img_select.setOnClickListener(new MyOnclick(position));
    }

    @Override
    public int getItemCount() {
        return fileBeen.size();
    }

    public class PhoneCleanHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_time,tv_memory;
        private ImageView img_select,img_name;
        public PhoneCleanHolder(View itemView) {
            super(itemView);
            tv_memory = (TextView) itemView.findViewById(R.id.item_fieshow_tvmemory);
            tv_time = (TextView) itemView.findViewById(R.id.item_fieshow_tvtime);
            tv_name = (TextView) itemView.findViewById(R.id.item_fieshow_tvname);
            img_name = (ImageView) itemView.findViewById(R.id.item_fieshow_imgname);
            img_select = (ImageView) itemView.findViewById(R.id.item_fieshow_imgselect);

        }
    }

    /**
     * 监听事件
     */
    class MyOnclick implements View.OnClickListener{
        int index;
        public MyOnclick(int index){
            this.index = index;
        }
        @Override
        public void onClick(View view) {
            PhoneCleanBean m = fileBeen.get(index);
            if (m.getIsselect()){
                m.setIsselect(false);
            }else {
                m.setIsselect(true);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
