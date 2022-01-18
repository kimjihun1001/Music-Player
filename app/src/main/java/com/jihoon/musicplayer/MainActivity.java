package com.jihoon.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jihoon.musicplayer.Model.ModelMusic;
import com.jihoon.musicplayer.Model.ModelPlaylist;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // context가 필요해서 Const가 아니라 여기에 선언함.
    public static int Size_1dp;

    public ModelPlaylist favorites;

    LinearLayout container_playlist;
    Context nowContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        com.jihoon.musicplayer.DBHelper helper;
        SQLiteDatabase db;
        helper = new com.jihoon.musicplayer.DBHelper(MainActivity.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        container_playlist = findViewById(R.id.container_music);

        // dp -> pixel
        size_1dp = ConvertDPtoPX(this, 1);

        // 전체 음악 리스트에 예시로 두 개 추가함 -> 나중에 DB에서 받아오도록 해야 함.
        com.jihoon.musicplayer.Model_music newMusic1 = new com.jihoon.musicplayer.Model_music();
        newMusic1.title = "Boss Bitch";
        List_Model_music.add(newMusic1);
        com.jihoon.musicplayer.Model_music newMusic2 = new com.jihoon.musicplayer.Model_music();
        newMusic2.title = "Lucky You";
        List_Model_music.add(newMusic2);

        // 초기 플레이리스트 하나 만들어둠.
        favorites = new com.jihoon.musicplayer.ModelPlaylist();
        favorites.name = "최애음악";
        // 플레이리스트에 음악 추가
        favorites.listOfMusic.add(newMusic2);
        List_ModelPlaylist.add(favorites);

        for(com.jihoon.musicplayer.ModelPlaylist ModelPlaylist: List_ModelPlaylist) {
            MakeNewPlaylist(ModelPlaylist);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        // 기존 화면 제거
        container_playlist.removeAllViews();

        // 리스트에서 플레이리스트 가져와서 화면에 표시
        for(com.jihoon.musicplayer.ModelPlaylist ModelPlaylist: List_ModelPlaylist) {
            MakeNewPlaylist(ModelPlaylist);
        }

    }

    // 플레이리스트 클릭 이벤트 핸들러: 내부 음악리스트 표시하는 액티비티로 넘어가게 함.
    public  void Click_Playlist(View view) {

        String nameOfPlaylist = view.getContentDescription().toString();
        Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);

        for (com.jihoon.musicplayer.ModelPlaylist ModelPlaylist: List_ModelPlaylist) {
            if (ModelPlaylist.name.equals(nameOfPlaylist))
            {
                intent.putExtra("name", ModelPlaylist.name);
            }
        }

        // intent 확인용
//        String name = intent.getExtras().getString("name");
//        String newTag = "For checking intent";
//        Toast.makeText(this, name, Toast.LENGTH_LONG).show();

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

                com.jihoon.musicplayer.ModelPlaylist newPlaylist = new com.jihoon.musicplayer.ModelPlaylist();
                newPlaylist.name = nameOfPlaylist;
                List_ModelPlaylist.add(newPlaylist);

                // 화면 업데이트
                onResume();
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

    @SuppressLint("ResourceAsColor")
    public void MakeNewPlaylist(com.jihoon.musicplayer.ModelPlaylist ModelPlaylist) {

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setContentDescription(ModelPlaylist.name);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Click_Playlist(view);
            }
        });
        // 레이아웃 방향 설정 방법!!
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size_1dp * 70));

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.music);
        // 휴우.. View의 크기 설정 방법!!
        imageView.setLayoutParams(new LinearLayout.LayoutParams(size_1dp * 70, ViewGroup.LayoutParams.MATCH_PARENT));
        // margin
        LinearLayout.LayoutParams imageViewLayoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
        int newMargin = MainActivity.size_1dp * 10;
        imageViewLayoutParams.setMargins(newMargin,newMargin,newMargin,newMargin);

        TextView textView = new TextView(this);
        textView.setText(ModelPlaylist.name);
        textView.setLayoutParams(new LinearLayout.LayoutParams(size_1dp * 200, ViewGroup.LayoutParams.MATCH_PARENT));
        // 하아.. 왜 이렇게 어려운 방식인걸까 ㅠㅠㅠ View의 margin 설정 방법!!!
        // 먼저, get으로 LayoutParams를 받아와서 속성 값을 조절해야 함.
        LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
        textViewLayoutParams.leftMargin = size_1dp * 9;
        // View의 Gravity 설정 방법!
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(1,20);
        textView.setTextColor(Color.BLACK);

        // 수정 버튼
        ImageButton imageButton = new ImageButton(this);
        // imageButton은 src말고 background로 설정해야 하는 듯. 근데 drawable로 가져와야 해서 get 함수 사용함.
        imageButton.setBackground(getDrawable(R.drawable.option));
        imageButton.setContentDescription(ModelPlaylist.name);
        imageButton.setLayoutParams(new LinearLayout.LayoutParams(size_1dp * 30, size_1dp *30));
        LinearLayout.LayoutParams imageButtonViewLayoutParams = (LinearLayout.LayoutParams)imageButton.getLayoutParams();
        imageButtonViewLayoutParams.setMargins(size_1dp * 10,size_1dp * 20,size_1dp * 10,size_1dp * 20);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_option(view);
            }
        });


        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.addView(imageButton);
        container_playlist.addView(linearLayout);
    }

    // 플레이리스트 설정 버튼 클릭 이벤트 핸들러
    public void Click_option(View view) {

        String nameOfPlaylist = view.getContentDescription().toString();

        AlertDialog.Builder alert_newPlaylist = new AlertDialog.Builder(this);

        alert_newPlaylist.setTitle("플레이리스트 정보 변경");
        alert_newPlaylist.setMessage("수정할 제목을 입력하세요.");

        // 사용자로부터 텍스트 입력받기 위한 박스
        final EditText input = new EditText(this);
        alert_newPlaylist.setView(input);

        // 확인 누를 경우
        alert_newPlaylist.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (ModelPlaylist modelPlaylist: List_ModelPlaylist) {
                    if (modelPlaylist.name.equals(nameOfPlaylist)) {
                        modelPlaylist.name = input.getText().toString();
                    }
                }
                // 화면 업데이트
                onResume();
            }
        });
        // 삭제 누를 경우
        alert_newPlaylist.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int indexOfPlaylistToRemove = 100;
                for (com.jihoon.musicplayer.ModelPlaylist ModelPlaylist: List_ModelPlaylist) {
                    if (ModelPlaylist.name.equals(nameOfPlaylist)) {
                        indexOfPlaylistToRemove = List_ModelPlaylist.indexOf(ModelPlaylist);
                    }
                }
                List_ModelPlaylist.remove(indexOfPlaylistToRemove);

                // 화면 업데이트
                onResume();
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

    public int ConvertDPtoPX(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

}