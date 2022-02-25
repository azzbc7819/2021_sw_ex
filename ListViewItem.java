package com.example.scheduler;

import android.widget.CheckBox;

import java.util.Random;

public class ListViewItem {
    //리스트뷰에 들어온 텍스트 정보 저장 및 수정 코드
    Random random = new Random();
    private String text;
    private final long rndid = random.nextLong();



    public void setText(String txt){
        this.text = txt;
    }

    public String getText(){
        return text;
    }

    public long getrndid(){
        return rndid;
    }


}
