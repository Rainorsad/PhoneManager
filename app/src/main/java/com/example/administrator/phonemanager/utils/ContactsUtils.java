package com.example.administrator.phonemanager.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.administrator.phonemanager.bean.ModelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ContactsUtils {
    //定义常量，节省重复引用的时间
    Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    String ID = ContactsContract.Contacts._ID;
    String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    //实现App之间数据共享
    private ContentResolver contentResolver;
    public ContactsUtils(Context context){
        contentResolver = context.getContentResolver();
    }
    //获取手机联系人
    public List<ModelBean> getContactsInfo(){
        List<ModelBean> modelBeen = new ArrayList<>();
//        Uri uri = Uri.parse("content://com.android.contacts/contacts");
//        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            ModelBean modelBean = new ModelBean();
            //获取联系人ID
            String _id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获得联系人名字
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //取得电话号码
            Cursor phone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + _id, null, null);
//            StringBuilder sb = new StringBuilder("contactid=").append(_id).append(name);
            StringBuilder sb = new StringBuilder("联系电话: ");
            while (phone.moveToNext()){
                int phoneFileIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneBunmer = phone.getString(phoneFileIndex);
                sb.append(phoneBunmer+"   ");
//                modelBean.setContacts_phonenumber(String.valueOf(sb));//获得多个号码显示
                modelBean.setContacts_phonenumber(phoneBunmer);
            }
            modelBean.setContacts_id(_id);
            modelBean.setContacts_name(name);

            modelBean.setType(ModelBean.A_type);
            modelBeen.add(modelBean);
        }
        cursor.close();
        return modelBeen;
    }
}
