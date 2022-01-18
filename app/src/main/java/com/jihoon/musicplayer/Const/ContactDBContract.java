package com.jihoon.musicplayer.Const;

// 데이터베이스 Contact를 위한 계약 클래스(Contract Class)
public class ContactDBContract {

    // 계약 클래스는 인스턴스를 만들 필요가 없기 때문에 생성자의 접근 제한자(Access Modifier)를 private으로 선언
    private  ContactDBContract() {};

    // 테이블: ALLMUSIC_T
    // 전체 음악 리스트 정보를 담고 있는 테이블
    public static final String TBL_ALLMUSIC = "ALLMUSIC_T";
    public static final String COL_TITLE = "TITLE";

    // ⭐️ 주석으로 QUERY 표시해두니까 좋네.
    // CREATE TABLE IF NOT EXISTS ALLMUSIC_T (TITLE TEXT NOT NULL)
    public static final String SQL_CREATE_TBL_ALLMUSIC = "CREATE TABLE IF NOT EXISTS " + TBL_ALLMUSIC + " " +
            "(" +
                COL_TITLE +     "TEXT NOT NULL" + "," +
            ")";

    public static final String SQL_SELECT_TBL_ALLMUSIC = "SELECT * FROM" + TBL_ALLMUSIC + " ";

    public static final String SQL_INSERT_TBL_ALLMUSIC = "INSERT OR REPLACE INTO " + TBL_ALLMUSIC + " " +
            "(" + COL_TITLE + ") VALUES " ;

    public static final String SQL_UPDATE_TBL_ALLMUSIC = "UPDATE" + TBL_ALLMUSIC + "SET ";

    public static final String SQL_DELETE_TBL_ALLMUSIC = "DELETE FROM " + TBL_ALLMUSIC + " ";


    // 테이블: MUSICOFPLAYLIST_T
    // 플레이리스트 내부 음악 리스트 정보를 담고 있는 테이블
    public static final String TBL_MUSICOFPLAYLIST = "MUSICOFPLAYLIST_T";
    // public static final String COL_TITLE = "TITLE";
    public static final String COL_FAVORITE = "FAVORITE";

    public static final String SQL_CREATE_TBL_MUSICOFPLAYLIST = "CREATE TABLE IF NOT EXISTS " + TBL_MUSICOFPLAYLIST + " " +
            "(" +
                COL_TITLE +     "TEXT NOT NULL" + "," +
                COL_FAVORITE +  "INTERGER" +
            ")";

    public static final String SQL_SELECT_TBL_MUSICOFPLAYLIST = "SELECT * FROM" + TBL_MUSICOFPLAYLIST + " ";

    public static final String SQL_INSERT_TBL_MUSICOFPLAYLIST = "INSERT OR REPLACE INTO " + TBL_MUSICOFPLAYLIST + " " +
            "(" + COL_TITLE + ", " + COL_FAVORITE + ") VALUES " ;

    public static final String SQL_UPDATE_TBL_MUSICOFPLAYLIST = "UPDATE" + TBL_MUSICOFPLAYLIST + "SET ";

    public static final String SQL_DELETE_TBL_MUSICOFPLAYLIST = "DELETE FROM " + TBL_MUSICOFPLAYLIST + " ";


    // 테이블: PLAYLIST_T
    // 전체 플레이리스트 정보를 담고 있는 테이블
    public static final String TBL_PLAYLIST = "PLAYLIST_T";
    // public static final String COL_TITLE = "TITLE";
    // public static final String COL_FAVORITE = "FAVORITE";

    public static final String SQL_CREATE_TBL_PLAYLIST = "CREATE TABLE IF NOT EXISTS " + TBL_PLAYLIST + " " +
            "(" +
            COL_TITLE +     "TEXT NOT NULL" + "," +
            COL_FAVORITE +  "INTERGER" +
            ")";

    public static final String SQL_SELECT_TBL_PLAYLIST = "SELECT * FROM" + TBL_PLAYLIST + " ";

    public static final String SQL_INSERT_TBL_PLAYLIST = "INSERT OR REPLACE INTO " + TBL_PLAYLIST + " " +
            "(" + COL_TITLE + ", " + COL_FAVORITE + ") VALUES " ;

    public static final String SQL_UPDATE_TBL_PLAYLIST = "UPDATE" + TBL_PLAYLIST + "SET ";

    public static final String SQL_DELETE_TBL_PLAYLIST = "DELETE FROM " + TBL_PLAYLIST + " ";
}
