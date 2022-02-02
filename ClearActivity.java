package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);



        Intent intent = getIntent();

        ListView listView = findViewById(R.id.list);
        ArrayList<String> data = new ArrayList<>(); //완료한 리스트 저장할 어레이리스트

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter); //activity_clear에 있는 리스트뷰에 적용,현재 클래스,

        int size = intent.getIntExtra("size",0);
        for(int i=0;i<size;i++){
            String str = intent.getStringExtra("key"+i);
            data.add(str);
        }

        adapter.notifyDataSetChanged();


    }
}