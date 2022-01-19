package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jihoon.musicplayer.Const.Const;
import com.jihoon.musicplayer.Model.ModelMusic;
import com.jihoon.musicplayer.Model.ModelPlaylist;

public class PlaylistActivity extends AppCompatActivity {

    LinearLayout container_music;
    TextView TextView_titleOfPlaylist;
    String titleOfPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        container_music = findViewById(R.id.container_music);
        TextView_titleOfPlaylist = findViewById(R.id.TextView_titleOfPlaylist);

        // 상단 플레이리스트 제목
        titleOfPlaylist = getIntent().getExtras().getString("title");
        TextView_titleOfPlaylist.setText(titleOfPlaylist);

        // 음악 리스트
        for(ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            // == 대신 equals()를 사용해야 함. 내용 자체를 비교하는 것이기 때문!!
            if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                ShowMusicList(modelPlaylist);
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
        String titleOfPlaylist = getIntent().getExtras().getString("title");
        for(ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            // == 대신 equals()를 사용해야 함. 내용 자체를 비교하는 것이기 때문!!
            if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                ShowMusicList(modelPlaylist);
                break;
            }
        }
    }

    public void Click_button_back(View view) {
        finish();
    }

    public void Click_button_newMusic(View view) {

        String titleOfPlaylist = getIntent().getExtras().getString("title");
        // 전체 음악 리스트를 보여주는 액티비티로 넘어가야 함.
        Intent intent = new Intent(getApplicationContext(), MusicListActivity.class);

        // intent에 플레이리스트 제목 추가
        for(ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                intent.putExtra("title", titleOfPlaylist);
                break;
            }
        }

        startActivity(intent);
    }

    public void ShowMusicList(ModelPlaylist modelPlaylist) {

        for(ModelMusic music: modelPlaylist.List_MusicOfPlaylist) {
            Toast.makeText(this, music.getTitle(), Toast.LENGTH_SHORT);
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
            textView.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.Size_1dp * 200, ViewGroup.LayoutParams.MATCH_PARENT));
            // margin
            LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
            textViewLayoutParams.leftMargin = MainActivity.Size_1dp * 9;
            // gravity
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(1,20);
            textView.setTextColor(Color.BLACK);

            // 수정 버튼
            ImageButton imageButton = new ImageButton(this);
            // imageButton은 src말고 background로 설정해야 하는 듯. 근데 drawable로 가져와야 해서 get 함수 사용함.
            imageButton.setBackground(getDrawable(R.drawable.option));
            imageButton.setContentDescription(music.getTitle());
            imageButton.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.Size_1dp * 30, MainActivity.Size_1dp *30));
            LinearLayout.LayoutParams imageButtonViewLayoutParams = (LinearLayout.LayoutParams)imageButton.getLayoutParams();
            imageButtonViewLayoutParams.setMargins(MainActivity.Size_1dp * 10,MainActivity.Size_1dp * 20,MainActivity.Size_1dp * 10,MainActivity.Size_1dp * 20);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Click_option(view);
                }
            });

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(imageButton);
            container_music.addView(linearLayout);
        }
    }

    public void Click_option(View view) {

        String titleOfMusic = view.getContentDescription().toString();

        AlertDialog.Builder alert_removeMusic = new AlertDialog.Builder(this);

        alert_removeMusic.setTitle("이 플레이리스트에서");
        alert_removeMusic.setMessage("음악을 삭제하시겠습니까?");

        // 삭제 누를 경우
        alert_removeMusic.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int indexOfMusicToRemove = 100;
                for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
                    if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                        for (ModelMusic modelMusic: modelPlaylist.List_MusicOfPlaylist) {
                            indexOfMusicToRemove = modelPlaylist.List_MusicOfPlaylist.indexOf(modelMusic);
                        }
                    }
                    modelPlaylist.List_MusicOfPlaylist.remove(indexOfMusicToRemove);
                }

                // 화면 업데이트
                onResume();
            }
        });
        // 취소 누를 경우
        alert_removeMusic.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 취소됨
            }
        });

        alert_removeMusic.show();
    }
}