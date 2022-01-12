package com.jihoon.musicplayer;

import static com.jihoon.musicplayer.MainActivity.List_Model_music;

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

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    LinearLayout container_allMusic;

    public static ArrayList<Model_music> List_MusicToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        List_MusicToAdd = new ArrayList<Model_music>();

        container_allMusic = findViewById(R.id.container_allMusic);

        ShowAllMusicList();

    }

    public void ShowAllMusicList() {
        for(Model_music music: MainActivity.List_Model_music) {
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
            textView.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.size_1dp * 210, ViewGroup.LayoutParams.MATCH_PARENT));
            // margin
            LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
            textViewLayoutParams.leftMargin = MainActivity.size_1dp * 9;
            // gravity
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(1,20);
            textView.setTextColor(Color.BLACK);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setContentDescription(music.title);
            checkBox.setText("");
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Check_checkBox(compoundButton);
                }
            });
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.size_1dp * 30, MainActivity.size_1dp * 30));
            checkBox.setBackgroundColor(Color.BLACK);

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(checkBox);
            container_allMusic.addView(linearLayout);
        }
    }

    public void Check_checkBox(CompoundButton compoundButton) {
        String titleOfMusic = compoundButton.getContentDescription().toString();
        Toast.makeText(this, titleOfMusic, Toast.LENGTH_SHORT).show();

        if (compoundButton.isChecked()) {
            for(Model_music model_music: MainActivity.List_Model_music) {
                if (model_music.title.equals(titleOfMusic)) {
                    List_MusicToAdd.add(model_music);
                }
            }
        }
        else {
            for(Model_music model_music: MainActivity.List_Model_music) {
                if (model_music.title.equals(titleOfMusic)) {
                    List_MusicToAdd.remove(model_music);
                }
            }
        }
    }

    public void Click_button_ok(View view) {

        if (!List_MusicToAdd.isEmpty()) {
            String nameOfPlaylist = getIntent().getExtras().getString("name");

            for(Model_playlist model_playlist: MainActivity.List_Model_playlist) {
                if (model_playlist.name.equals(nameOfPlaylist)) {
                    for (Model_music musicToAdd: List_MusicToAdd) {
                        if (!model_playlist.listOfMusic.contains(musicToAdd)) {
                            Toast.makeText(this, "더함!", Toast.LENGTH_SHORT).show();;
                            model_playlist.listOfMusic.add(musicToAdd);
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