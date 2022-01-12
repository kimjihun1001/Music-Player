package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlaylistActivity extends AppCompatActivity {

    LinearLayout container_music;
    TextView TextView_nameOfPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        container_music = findViewById(R.id.container_music);
        TextView_nameOfPlaylist = findViewById(R.id.TextView_nameOfPlaylist);

        // 상단 플레이리스트 제목
        String nameOfPlaylist = getIntent().getExtras().getString("name");
        TextView_nameOfPlaylist.setText(nameOfPlaylist);

        // 음악 리스트
        for(Model_playlist model_playlist: MainActivity.List_Model_playlist) {
            // == 대신 equals()를 사용해야 함. 내용 자체를 비교하는 것이기 때문!!
            if (model_playlist.name.equals(nameOfPlaylist)) {
                ShowMusicList(model_playlist);
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        container_music = findViewById(R.id.container_music);
        container_music.removeAllViews();

        // 음악 리스트 업데이트
        String nameOfPlaylist = getIntent().getExtras().getString("name");
        for(Model_playlist model_playlist: MainActivity.List_Model_playlist) {
            // == 대신 equals()를 사용해야 함. 내용 자체를 비교하는 것이기 때문!!
            if (model_playlist.name.equals(nameOfPlaylist)) {
                ShowMusicList(model_playlist);
                break;
            }
        }
    }

    public void Click_button_back(View view) {
        finish();
    }

    public void Click_button_newMusic(View view) {

        String nameOfPlaylist = getIntent().getExtras().getString("name");
        // 전체 음악 리스트를 보여주는 액티비티로 넘어가야 함.
        Intent intent = new Intent(getApplicationContext(), MusicListActivity.class);

        // 플레이리스트의 음악 리스트에 추가
        for(Model_playlist model_playlist: MainActivity.List_Model_playlist) {
            if (model_playlist.name.equals(nameOfPlaylist)) {
                intent.putExtra("name", nameOfPlaylist);
                break;
            }
        }

        startActivity(intent);
    }

    public void ShowMusicList(Model_playlist model_playlist) {

        for(Model_music music: model_playlist.listOfMusic) {
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

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            container_music.addView(linearLayout);
        }

    }
}