package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jihoon.musicplayer.Const.Const;
import com.jihoon.musicplayer.Model.ModelMusic;
import com.jihoon.musicplayer.Model.ModelPlaylist;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    LinearLayout container_allMusic;

    public static ArrayList<ModelMusic> List_MusicToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        List_MusicToAdd = new ArrayList<ModelMusic>();

        container_allMusic = findViewById(R.id.container_allMusic);

        ShowAllMusicList();

    }

    public void ShowAllMusicList() {
        for(ModelMusic music: Const.List_ModelMusic) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setContentDescription(music.getTitle());
//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    Click_Playlist(view);
//                }
//            });
            // 레이아웃 방향 설정 방법!!
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MainActivity.Size_1dp * 70));

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.music);
            // width, height
            imageView.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.Size_1dp * 70, ViewGroup.LayoutParams.MATCH_PARENT));
            // margin
            LinearLayout.LayoutParams imageViewLayoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
            int newMargin = MainActivity.Size_1dp * 10;
            imageViewLayoutParams.setMargins(newMargin,newMargin,newMargin,newMargin);

            TextView textView = new TextView(this);
            textView.setText(music.getTitle());
            textView.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.Size_1dp * 210, ViewGroup.LayoutParams.MATCH_PARENT));
            // margin
            LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
            textViewLayoutParams.leftMargin = MainActivity.Size_1dp * 9;
            // gravity
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(1,20);
            textView.setTextColor(Color.BLACK);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setContentDescription(music.getTitle());
            checkBox.setText("");
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Check_checkBox(compoundButton);
                }
            });
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.Size_1dp * 30, MainActivity.Size_1dp * 30));
            checkBox.setBackgroundColor(Color.BLACK);

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(checkBox);
            container_allMusic.addView(linearLayout);
        }
    }

    public void Check_checkBox(CompoundButton compoundButton) {
        Toast.makeText(this, "checkbox", Toast.LENGTH_SHORT);

        String titleOfMusic = compoundButton.getContentDescription().toString();

        if (compoundButton.isChecked()) {
            for(ModelMusic modelMusic: Const.List_ModelMusic) {
                if (modelMusic.getTitle().equals(titleOfMusic)) {
                    List_MusicToAdd.add(modelMusic);
                }
            }
        }
        else {
            for(ModelMusic modelMusic: Const.List_ModelMusic) {
                if (modelMusic.getTitle().equals(titleOfMusic)) {
                    List_MusicToAdd.remove(modelMusic);
                }
            }
        }
    }

    public void Click_button_ok(View view) {
        if (!List_MusicToAdd.isEmpty()) {
            String nameOfPlaylist = getIntent().getExtras().getString("name");

            for(ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
                if (modelPlaylist.getTitle().equals(nameOfPlaylist)) {
                    for (ModelMusic musicToAdd: List_MusicToAdd) {
                        if (!modelPlaylist.List_MusicOfPlaylist.contains(musicToAdd)) {
                            modelPlaylist.List_MusicOfPlaylist.add(musicToAdd);
                        }
                    }
                }
            }
            // DB 업데이트
            MainActivity.save_DB();
        }
        finish();
    }

    public void Click_button_back(View view) {
        finish();
    }
}