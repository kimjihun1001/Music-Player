package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlaylistActivity extends AppCompatActivity {

    LinearLayout container_musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        container_musicList = findViewById(R.id.container_playList);

        LinearLayout linearLayout = new LinearLayout(this);
        String nameOfPlaylist = getIntent().getExtras().getString("name");
        TextView textView = new TextView(this);
        textView.setText(nameOfPlaylist);
        linearLayout.addView(textView);
        container_musicList.addView(linearLayout);
    }
}