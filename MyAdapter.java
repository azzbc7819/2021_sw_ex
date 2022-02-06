package com.example.scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private final ArrayList<ListViewItem> itemlist = new ArrayList<ListViewItem>();


    public MyAdapter(){
    }

    public int getCount(){
        return itemlist.size();
    }

    public View getView(int i, View view, ViewGroup viewgroup){
        final int pos = i;
        final Context context = viewgroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,viewgroup,false);
        }

        TextView tv = view.findViewById(R.id.textView);

        ListViewItem item = itemlist.get(i);

        tv.setText(item.getText());

        CheckBox checkBox = view.findViewById(R.id.checkBox);

        return view;

    }

    public long getItemId(int i){
        return i;
    }

    public Object getItem(int i){
        return itemlist.get(i);
    }

    public void addItem(String str){
        ListViewItem item = new ListViewItem();

        item.setText(str);

        itemlist.add(item);
    }

    public void removeItem(int i){
        itemlist.remove(i);
    }

    public String getInfo(int i){
        return itemlist.get(i).getText().toString();

    }

    public static void copyDatabase(Context context){
        String DB_PATH = "/data/data/" + context.getPackageName() + "/databases";

        try{
            File fDir = new File(DB_PATH);
            if(!fDir.exists()){
                fDir.mkdir();
            }

            String strOutFile = DB_PATH + "todolistapp.db";
            InputStream inputStream = context.getAssets().open("todolistapp.db");
            OutputStream outputStream = new FileOutputStream(strOutFile);

            byte[] mBuffer = new byte[1024];
            int mLength;
            while((mLength = inputStream.read(mBuffer)) > 0){
                outputStream.write(mBuffer,0,mLength);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
