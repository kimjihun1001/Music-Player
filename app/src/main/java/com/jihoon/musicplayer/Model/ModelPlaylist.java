package com.jihoon.musicplayer.Model;

import java.util.ArrayList;

public class ModelPlaylist {
    private String title;
    private boolean isFavorite;
    public ArrayList<ModelMusic> List_MusicOfPlaylist;

    public ModelPlaylist() {
        isFavorite = false;
        List_MusicOfPlaylist = new ArrayList<ModelMusic>();
    }

    // JAVA에는 C# property 같은 건 없어서 이렇게 get, set 따로 써야 함.
    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public boolean getIsFavorite() { return this.isFavorite; }
    public void setIsFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }
}