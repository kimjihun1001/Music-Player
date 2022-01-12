package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    public static ArrayList<Model_music> List_Model_music = new ArrayList<Model_music>();
    LinearLayout container_music;

    public static ArrayList<CheckBox> List_CheckBox = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        container_music = findViewById(R.id.container_music);

        // 전체 음악 리스트에 예시로 하나 추가함 -> 나중에 DB에서 받아오도록 해야 함.
        Model_music newMusic = new Model_music();
        newMusic.title = "Boss Bitch";
        List_Model_music.add(newMusic);

        ShowAllMusicList();

    }

    public void ShowAllMusicList() {
        for(Model_music music: List_Model_music) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setContentDescription(music.title);
//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    Click_Playlist(view);
//                }
//            });
            // 레이아웃 방향 설정 방법!!
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MainActivity.size_1dp * 70));

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.music);
            // width, height
            imageView.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.size_1dp * 70, ViewGroup.LayoutParams.MATCH_PARENT));
            // margin
            LinearLayout.LayoutParams imageViewLayoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
            int newMargin = MainActivity.size_1dp * 10;
            imageViewLayoutParams.setMargins(newMargin,newMargin,newMargin,newMargin);

            TextView textView = new TextView(this);
            textView.setText(music.title);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // margin
            LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
            textViewLayoutParams.leftMargin = MainActivity.size_1dp * 9;
            // gravity
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(1,20);
            textView.setTextColor(Color.BLACK);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setContentDescription(music.title);
            List_CheckBox.add(checkBox);

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            container_music.addView(linearLayout);
        }
    }

    public void Click_button_ok(View view) {
        if (List_CheckBox.size() != 0) {
            String nameOfPlaylist = getIntent().getExtras().getString("name");

            for(Model_playlist model_playlist: MainActivity.List_Model_playlist) {
                if (model_playlist.equals(nameOfPlaylist)) {
                    for(CheckBox checkBox: List_CheckBox) {
                        if (checkBox.isChecked())
                        {
                            String titleOfMusic = checkBox.getContentDescription().toString();
                            for(Model_music model_music: List_Model_music) {
                                if (titleOfMusic.equals(model_music.title)) {
                                    if (model_playlist.listOfMusic.contains(model_music)) {

                                    }
                                    else {
                                        model_playlist.listOfMusic.add(model_music);

                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        finish();
    }

    public void Click_button_back(View view) {
        finish();
    }
}