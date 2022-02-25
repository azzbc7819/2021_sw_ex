package com.example.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteClosable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText ed;
    Button add, del, clear, goButton;
    ListView list;
    MyAdapter adapter;
    SQLiteDatabase todoDB;
    int cnt;


    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed = findViewById(R.id.editText);
        add = findViewById(R.id.Addbt);
        del = findViewById(R.id.Deletebt);
        clear = findViewById(R.id.Clearbt);
        goButton = findViewById(R.id.gotobt);
        cnt = -1; //인덱스는 0부터 시작 추가 되면 그때부터 시작

        adapter = new MyAdapter(); //메인 화면에 뷰 그룹 가져오는 것, 리스트뷰의 정보들 가져오는 것

        list = findViewById(R.id.list);
        list.setAdapter(adapter); //메인 화면에서 리스트뷰 가져와서 Myadapter 코드 연결

        ArrayList<String> clearinfo = new ArrayList<>();

        adapter.copyDatabase(this);

        todoDB = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.scheduler/databasestodolistapp_re.db",null);

        Cursor cursor = todoDB.rawQuery("SELECT content FROM todo",null);

        while(cursor.moveToNext()){
            String todo = cursor.getString(0);

            adapter.addItem(todo);
            adapter.notifyDataSetChanged();
        }

            add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = ed.getText().toString();

                adapter.addItem(str);
                adapter.notifyDataSetChanged();
                cnt++;

                String dbinsert = String.format("INSERT INTO todo (content, itemid, addflag,delflag) VALUES ('%s',%d,1,0);", str,adapter.getRndId(cnt));

                todoDB.execSQL(dbinsert);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this); //안내 화면 띄울 alertdialog 세팅할 빌더 생성
        builder.setTitle("안내");
        builder.setMessage("삭제하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SparseBooleanArray checkedItems = list.getCheckedItemPositions(); //선택된 아이템들을 가져오기 위한 코드

                for(int j=adapter.getCount();j>=0;j--){
                    try{
                        if(checkedItems.get(j)){
                            String sqlupdate = String.format("UPDATE todo SET delflag = 1 WHERE itemid = %d",adapter.getRndId(j));
                            System.out.println(adapter.getRndId(j));
                            todoDB.execSQL(sqlupdate);
                            adapter.removeItem(j);
                            cnt--;
                        }
                    }catch(Exception e){
                    }
                }
                list.clearChoices();
                adapter.notifyDataSetChanged();

                String sqldelete = "DELETE FROM todo WHERE delflag = 1";
                todoDB.execSQL(sqldelete);

            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //아니오 누르면 아무 일도 안 일어나고 알림창 꺼짐요
            }
        });


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create(); //del 버튼 누를 시 알림창 생성
                dialog.show(); //alertdialog 화면에 띄우기

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItems = list.getCheckedItemPositions(); //선택된 아이템들을 가져오기 위한 코드

                for(int j=adapter.getCount();j>=0;j--){
                    try{
                        if(checkedItems.get(j)){
                            String sqlupdate = String.format("UPDATE todo SET delflag = 1 WHERE itemid = %d",adapter.getRndId(j));
                            System.out.println(adapter.getRndId(j));
                            todoDB.execSQL(sqlupdate);
                            adapter.removeItem(j);
                            cnt--;
                        }
                    }catch(Exception e){
                    }
                }
                list.clearChoices();
                adapter.notifyDataSetChanged();

                Cursor cursor = todoDB.rawQuery("SELECT content,itemid,addflag,delflag FROM todo WHERE delflag = 1",null);

                while(cursor.moveToNext()){
                    String content = cursor.getString(0);
                    int rndid = cursor.getInt(1);

                    String insertclear = String.format("INSERT INTO finish (content,itemid,addflag) VALUES ('%s','%d',1)", content,rndid);
                    todoDB.execSQL(insertclear);

                }

                String sqldelete = "DELETE FROM todo WHERE delflag = 1";
                todoDB.execSQL(sqldelete);



            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClearActivity.class); //현재 코드에서 ClearActivity.class로 이동
                /*for(int i=0;i<clearinfo.size();i++){
                    String str = clearinfo.get(i); //완료한 리스트 가져오기
                    intent.putExtra("key"+i,str); //인텐트로 전달
                }
                intent.putExtra("size",clearinfo.size());*/

                intent.putExtra("todoDB", String.valueOf(todoDB));
                startActivity(intent); //꼭 스타트액티비티는 다 전달해주고 나서!!!!!!!!!!!!!!해라!! put다 하고 나서!!!!!!!!!!!!!!!
            }
        });





    }
}