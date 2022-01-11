package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Model_playlist> List_Model_playlist = new ArrayList<Model_playlist>();
    public Model_playlist favorites = new Model_playlist();

    public int size_1dp;

    LinearLayout container_playlist;
    Context nowContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // dp -> pixel
        size_1dp = ConvertDPtoPX(this, 1);

        // 초기 플레이리스트 하나 만들어둠.
        container_playlist = findViewById(R.id.container_playList);
        favorites.name = "최애음악";
        favorites.listOfMusic.add("Lucky You");
        List_Model_playlist.add(favorites);

        for(Model_playlist model_playlist: List_Model_playlist) {
            MakeNewPlaylist(model_playlist);
        }
    }

    // 플레이리스트 클릭 이벤트 핸들러: 내부 음악리스트 표시하는 액티비티로 넘어가게 함.
    public  void Click_Playlist(View view) {

        String nameOfPlaylist = view.getContentDescription().toString();
        Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);

        for (Model_playlist model_playlist: List_Model_playlist) {
            if (model_playlist.name == nameOfPlaylist)
            {
                intent.putExtra("name", model_playlist.name);
                intent.putExtra("listOfMusic", model_playlist.listOfMusic);
            }
        }

        // intent 확인용
        String name = intent.getExtras().getString("name");
        String newTag = "For checking intent";
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();

        startActivity(intent);

    }

    // 새로운 플레이리스트 생성 이벤트 핸들러
    public void Click_newPlaylist(View view) {

        AlertDialog.Builder alert_newPlaylist = new AlertDialog.Builder(this);

        alert_newPlaylist.setTitle("새로운 플레이리스트 생성");
        alert_newPlaylist.setMessage("제목을 입력하세요.");

        // 사용자로부터 텍스트 입력받기 위한 박스
        final EditText input = new EditText(this);
        alert_newPlaylist.setView(input);
        // Q. 왜 여기는 addView가 아니라 setView를 사용해야 할까?

        // 확인 누를 경우
        alert_newPlaylist.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nameOfPlaylist = input.getText().toString();

                Model_playlist newPlaylist = new Model_playlist();
                newPlaylist.name = nameOfPlaylist;
                List_Model_playlist.add(newPlaylist);

                MakeNewPlaylist(newPlaylist);
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

    public void MakeNewPlaylist(Model_playlist model_playlist) {

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setContentDescription(model_playlist.name);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Click_Playlist(view);
            }
        });
        // 레이아웃 방향 설정 방법!!
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size_1dp * 90));

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.music);
        // 휴우.. View의 크기 설정 방법!!
        imageView.setLayoutParams(new LinearLayout.LayoutParams(size_1dp * 90, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView textView = new TextView(this);
        textView.setText(model_playlist.name);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 하아.. 왜 이렇게 어려운 방식인걸까 ㅠㅠㅠ View의 margin 설정 방법!!!
        // 먼저, get으로 LayoutParams를 받아와서 속성 값을 조절해야 함.
        LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
        textViewLayoutParams.leftMargin = size_1dp * 9;
        // View의 Gravity 설정 방법!
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(1,20);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        container_playlist.addView(linearLayout);
    }

    public static int ConvertDPtoPX(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

}