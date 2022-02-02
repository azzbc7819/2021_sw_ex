package com.example.scheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteClosable;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText ed;
    Button add, del, clear, goButton;
    ListView list;
    MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed = findViewById(R.id.editText);
        add = findViewById(R.id.Addbt);
        del = findViewById(R.id.Deletebt);
        clear = findViewById(R.id.Clearbt);
        goButton = findViewById(R.id.gotobt);

        adapter = new MyAdapter(); //메인 화면에 뷰 그룹 가져오는 것, 리스트뷰의 정보들 가져오는 것

        list = findViewById(R.id.list);
        list.setAdapter(adapter); //메인 화면에서 리스트뷰 가져와서 Myadapter 코드 연결

        ArrayList<String> clearinfo = new ArrayList<>();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = ed.getText().toString();
                adapter.addItem(str);
                adapter.notifyDataSetChanged(); //갱신

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this); //안내 화면 띄울 alertdialog 세팅할 빌더 생성
        builder.setTitle("안내");
        builder.setMessage("삭제하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SparseBooleanArray checkedItems = list.getCheckedItemPositions(); //선택된 아이템들을 가져오기 위한 코드
                for (int j = 0; j < adapter.getCount(); j++) { //아이템리스트 개수만큼 반복
                    if (checkedItems.get(j)) {
                        adapter.removeItem(j); //리스트뷰의 아이템들을 처음부터 끝까지 확인해 체크 유무를 확인하고 체크되어 있으면 삭제

                    }
                    list.clearChoices(); //체크 상태 초기화
                    adapter.notifyDataSetChanged(); //갱신
                }
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
                SparseBooleanArray checkedItems = list.getCheckedItemPositions();
                for (int j = 0; j < adapter.getCount(); j++) {
                    if (checkedItems.get(j)) {
                        clearinfo.add(adapter.getInfo(j)); //j번째 텍스트 어레이리스트에 추가
                        adapter.removeItem(j); //하고 리스트뷰에서는 삭제
                    }
                    list.clearChoices();
                    adapter.notifyDataSetChanged();
                }

            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClearActivity.class); //현재 코드에서 ClearActivity.class로 이동
                for(int i=0;i<clearinfo.size();i++){
                    String str = clearinfo.get(i); //완료한 리스트 가져오기
                    intent.putExtra("key"+i,str); //인텐트로 전달
                }
                intent.putExtra("size",clearinfo.size());
                startActivity(intent); //꼭 스타트액티비티는 다 전달해주고 나서!!!!!!!!!!!!!!해라!! put다 하고 나서!!!!!!!!!!!!!!!
            }
        });





    }
}