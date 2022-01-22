package com.jihoon.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jihoon.musicplayer.Const.Const;
import com.jihoon.musicplayer.Const.MusicPlayerDBContract;
import com.jihoon.musicplayer.DB.MusicPlayerDBHelper;
import com.jihoon.musicplayer.Model.ModelMusic;
import com.jihoon.musicplayer.Model.ModelPlaylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    // context가 필요해서 Const가 아니라 여기에 선언함.
    public static int Size_1dp;

    private LinearLayout container_playlist;
    private LinearLayout container_favoritePlaylist;

    public static MusicPlayerDBHelper musicPlayerDBHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tab
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;
        // "Tab Spec" 태그(Tag)를 가진 TabSpec 객체 생성.
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec") ;

        // 탭이 눌려졌을 때 FrameLayout에 표시될 Content 뷰에 대한 리소스 id 지정.
        ts1.setContent(R.id.tab1) ;

        // 탭에 표시될 문자열 지정.
        ts1.setIndicator("전체") ;

        // TabHost에 탭 추가.
        tabHost1.addTab(ts1)  ;

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec") ;
        ts2.setContent(R.id.tab2) ;
        ts2.setIndicator("관심") ;
        tabHost1.addTab(ts2)  ;


        // DBHelper 만들기
        init_tables();
        // 처음 한 번만 실행 - 기본 음악리스트 만들기
        // init_allMusic();
        // DB 불러오기
        load_DB();

        // dp -> pixel
        Size_1dp = ConvertDPtoPX(this, 1);

        container_playlist = findViewById(R.id.container_playlist);
        container_favoritePlaylist = findViewById(R.id.container_favoritePlaylist);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        // 기존 화면 제거
        container_playlist.removeAllViews();
        container_favoritePlaylist.removeAllViews();

        // 리스트에서 플레이리스트 가져와서 화면에 표시
        for(ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            MakeNewPlaylist(modelPlaylist);
        }
    }

    // 플레이리스트 클릭 이벤트 핸들러: 내부 음악리스트 표시하는 액티비티로 넘어가게 함.
    public  void Click_Playlist(View view) {

        String titleOfPlaylist = view.getContentDescription().toString();
        Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);

        for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            if (modelPlaylist.getTitle().equals(titleOfPlaylist))
            {
                intent.putExtra("title", modelPlaylist.getTitle());
            }
        }

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
                String titleOfPlaylist = input.getText().toString();

                // 플레이리스트 제목 중복 체크
                for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
                    if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {

                    }
                    else {
                        ModelPlaylist newPlaylist = new ModelPlaylist();
                        newPlaylist.setTitle(titleOfPlaylist);
                        Const.List_ModelPlaylist.add(newPlaylist);

                        // DB 업데이트
                        save_DB();

                        // 화면 업데이트
                        onResume();
                    }
                }

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
    public void MakeNewPlaylist(ModelPlaylist modelPlaylist) {

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setContentDescription(modelPlaylist.getTitle());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Click_Playlist(view);
            }
        });
        // 레이아웃 방향 설정 방법!!
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Size_1dp * 70));

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.music);
        // 휴우.. View의 크기 설정 방법!!
        imageView.setLayoutParams(new LinearLayout.LayoutParams(Size_1dp * 70, ViewGroup.LayoutParams.MATCH_PARENT));
        // margin
        LinearLayout.LayoutParams imageViewLayoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
        int newMargin = MainActivity.Size_1dp * 10;
        imageViewLayoutParams.setMargins(newMargin,newMargin,newMargin,newMargin);

        TextView textView = new TextView(this);
        textView.setText(modelPlaylist.getTitle());
        textView.setLayoutParams(new LinearLayout.LayoutParams(Size_1dp * 170, ViewGroup.LayoutParams.MATCH_PARENT));
        // 하아.. 왜 이렇게 어려운 방식인걸까 ㅠㅠㅠ View의 margin 설정 방법!!!
        // 먼저, get으로 LayoutParams를 받아와서 속성 값을 조절해야 함.
        LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
        textViewLayoutParams.leftMargin = Size_1dp * 9;
        // View의 Gravity 설정 방법!
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(1,20);
        textView.setTextColor(Color.BLACK);

        // 하트 버튼
        ImageButton imageButton_fav = new ImageButton(this);
        if (modelPlaylist.getIsFavorite() == true) {
            imageButton_fav.setBackground(getDrawable(R.drawable.ic_baseline_favorite_24));
        }
        else {
            imageButton_fav.setBackground(getDrawable(R.drawable.ic_baseline_favorite_border_24));
        }
        imageButton_fav.setContentDescription(modelPlaylist.getTitle());
        imageButton_fav.setLayoutParams(new LinearLayout.LayoutParams(Size_1dp * 30, Size_1dp *30));
        LinearLayout.LayoutParams imageButton_favViewLayoutParams = (LinearLayout.LayoutParams)imageButton_fav.getLayoutParams();
        imageButton_favViewLayoutParams.setMargins(Size_1dp * 10,Size_1dp * 20,Size_1dp * 10,Size_1dp * 20);
        imageButton_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_heart(view);
            }
        });

        // 수정 버튼
        ImageButton imageButton = new ImageButton(this);
        // imageButton은 src말고 background로 설정해야 하는 듯. 근데 drawable로 가져와야 해서 get 함수 사용함.
        imageButton.setBackground(getDrawable(R.drawable.option));
        imageButton.setContentDescription(modelPlaylist.getTitle());
        imageButton.setLayoutParams(new LinearLayout.LayoutParams(Size_1dp * 30, Size_1dp *30));
        LinearLayout.LayoutParams imageButtonViewLayoutParams = (LinearLayout.LayoutParams)imageButton.getLayoutParams();
        imageButtonViewLayoutParams.setMargins(Size_1dp * 10,Size_1dp * 20,Size_1dp * 10,Size_1dp * 20);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_option(view);
            }
        });

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.addView(imageButton_fav);
        linearLayout.addView(imageButton);
        container_playlist.addView(linearLayout);
        if (modelPlaylist.getIsFavorite() == true) {
            container_favoritePlaylist.addView(linearLayout);
        }

    }

    public void Click_heart(View view) {
        String titleOfPlaylist = view.getContentDescription().toString();

        for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                if (modelPlaylist.getIsFavorite() == true) {
                    modelPlaylist.setIsFavorite(false);
                    view.setBackground(getDrawable(R.drawable.ic_baseline_favorite_border_24));
                }
                else {
                    modelPlaylist.setIsFavorite(true);
                    view.setBackground(getDrawable(R.drawable.ic_baseline_favorite_24));
                }
            }
        }

        // DB 업데이트
        save_DB();

        // 화면 업데이트
        onResume();
    }

    // 플레이리스트 설정 버튼 클릭 이벤트 핸들러
    public void Click_option(View view) {

        String titleOfPlaylist = view.getContentDescription().toString();

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
                for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
                    if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                        modelPlaylist.setTitle(input.getText().toString());
                    }
                }
                // DB 업데이트
                save_DB();

                // 화면 업데이트
                onResume();
            }
        });
        // 삭제 누를 경우
        alert_newPlaylist.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int indexOfPlaylistToRemove = 100;
                for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
                    if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                        indexOfPlaylistToRemove = Const.List_ModelPlaylist.indexOf(modelPlaylist);
                    }
                }
                Const.List_ModelPlaylist.remove(indexOfPlaylistToRemove);

                // DB 업데이트
                save_DB();

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

    // DB 관련
    private void init_tables() {
        musicPlayerDBHelper = new MusicPlayerDBHelper(this);
    }
    private void load_DB() {
        SQLiteDatabase db = musicPlayerDBHelper.getReadableDatabase();

        Cursor cursorForAllMusic = db.rawQuery(MusicPlayerDBContract.SQL_SELECT_TBL_ALLMUSIC, null);
        while (cursorForAllMusic.moveToNext()) {
            String title = cursorForAllMusic.getString(0);
            ModelMusic modelMusic = new ModelMusic();
            modelMusic.setTitle(title);
            Const.List_ModelMusic.add(modelMusic);
        }

        Cursor cursorForPlaylist = db.rawQuery(MusicPlayerDBContract.SQL_SELECT_TBL_PLAYLIST, null);
        while (cursorForPlaylist.moveToNext()) {
            String title = cursorForPlaylist.getString(0);
            boolean isFavorite = ((cursorForPlaylist.getInt(1) == 1) ? true : false);
            ModelPlaylist modelPlaylist = new ModelPlaylist();
            modelPlaylist.setTitle(title);
            modelPlaylist.setIsFavorite(isFavorite);
            Const.List_ModelPlaylist.add(modelPlaylist);
        }

        Cursor cursorForMusicOfPlaylist = db.rawQuery(MusicPlayerDBContract.SQL_SELECT_TBL_MUSICOFPLAYLIST, null);
        while (cursorForMusicOfPlaylist.moveToNext()) {
            String titleOfPlaylist = cursorForMusicOfPlaylist.getString(0);
            String title = cursorForMusicOfPlaylist.getString(1);
            ModelMusic modelMusic = new ModelMusic();
            modelMusic.setTitle(title);

            for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
                if (modelPlaylist.getTitle().equals(titleOfPlaylist)) {
                    modelPlaylist.List_MusicOfPlaylist.add(modelMusic);
                }
            }
        }
    }
    public static void save_DB() {
        SQLiteDatabase db = musicPlayerDBHelper.getWritableDatabase();

        db.execSQL(MusicPlayerDBContract.SQL_DELETE_TBL_PLAYLIST);
        db.execSQL(MusicPlayerDBContract.SQL_DELETE_TBL_MUSICOFPLAYLIST);

        for (ModelPlaylist modelPlaylist: Const.List_ModelPlaylist) {
            db.execSQL(MusicPlayerDBContract.SQL_INSERT_TBL_PLAYLIST +
                    "(" +
                    "'" + modelPlaylist.getTitle() + "', " +
                    ((modelPlaylist.getIsFavorite() == true) ? 1 : 0) +
                    ")"
            );

            for (ModelMusic modelMusic: modelPlaylist.List_MusicOfPlaylist) {
                db.execSQL(MusicPlayerDBContract.SQL_INSERT_TBL_MUSICOFPLAYLIST +
                        "(" +
                        "'" + modelPlaylist.getTitle() + "', " +
                        "'" + modelMusic.getTitle() + "'" +
                        ")"
                );
            }
        }
    }
    private void init_allMusic() {
         SQLiteDatabase db = musicPlayerDBHelper.getWritableDatabase();
         db.execSQL(MusicPlayerDBContract.SQL_DELETE_TBL_ALLMUSIC);
         ArrayList<String> List_titleOfMusic = new ArrayList<String>(Arrays.asList("Boss bitch", "White Christmas", "Lucky you"));

        for (String titleOfMusic: List_titleOfMusic) {
            db.execSQL(MusicPlayerDBContract.SQL_INSERT_TBL_ALLMUSIC +
                    "(" +
                    "'" + titleOfMusic + "'" +
                    ")"
            );
        }
    }
}