package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Model_playlist> List_Model_playlist = new ArrayList<Model_playlist>();

    LinearLayout container_playlist;
    Context nowContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container_playlist = findViewById(R.id.container_playlist);
        nowContext = container_playlist.getContext();

        for(Model_playlist model_playlist: List_Model_playlist) {
            LinearLayout linearLayout = new LinearLayout(nowContext);
            TextView textView = new TextView(nowContext);
            textView.setText(model_playlist.name);
            linearLayout.addView(textView);
            container_playlist.addView(linearLayout);
        }
    }

    public void Click_btn_newPlaylist(View view) {

        AlertDialog.Builder alert_newPlaylist = new AlertDialog.Builder(this);

        alert_newPlaylist.setTitle("새로운 플레이리스트 생성");
        alert_newPlaylist.setMessage("제목을 입력하세요.");

        // 사용자로부터 텍스트 입력받기 위한 박스
        final EditText input = new EditText(this);
        alert_newPlaylist.setView(input);

        // 확인 누를 경우
        alert_newPlaylist.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();

                Model_playlist newPlaylist = new Model_playlist();
                newPlaylist.name = value;
                List_Model_playlist.add(newPlaylist);

                LinearLayout linearLayout = new LinearLayout(nowContext);
                TextView textView = new TextView(nowContext);
                textView.setText(newPlaylist.name);
                linearLayout.addView(textView);
                container_playlist.addView(linearLayout);

            }
        });

        // 취소 누를 경우
        alert_newPlaylist.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 취소됨
            }
        });

        alert_newPlaylist.show();
    }
}