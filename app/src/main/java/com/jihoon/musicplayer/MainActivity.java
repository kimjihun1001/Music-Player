package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Click_btn_newPlaylist(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("인사말").setMessage("반갑습니다");

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}