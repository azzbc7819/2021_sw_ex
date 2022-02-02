package com.example.scheduler;

import android.widget.CheckBox;

public class ListViewItem {
    //리스트뷰에 들어온 텍스트 정보 저장 및 수정 코드
    private String text;

    public void setText(String txt){
        this.text = txt;
    }

    public String getText(){
        return text;
    }


}
