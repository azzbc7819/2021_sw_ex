package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClearActivity extends AppCompatActivity {
    SQLiteDatabase todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);



        Intent intent = getIntent();


        todoDB = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.scheduler/databasestodolistapp_re.db",null);
        ListView listView = findViewById(R.id.list);
        ArrayList<String> data = new ArrayList<>(); //완료한 리스트 저장할 어레이리스트

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter); //activity_clear에 있는 리스트뷰에 적용,현재 클래스,

        //int size = intent.getIntExtra("size",0);
        Cursor cursor = todoDB.rawQuery("SELECT content FROM finish", null);

        while(cursor.moveToNext()){
            String str = cursor.getString(0);
            data.add(str);
        }
        /*for(int i=0;i<size;i++){
            //String str = intent.getStringExtra("key"+i);
            data.add(str);
        }*/

        adapter.notifyDataSetChanged();


    }
}