package com.jihoon.musicplayer.Model;

import java.util.ArrayList;

public class ModelPlaylist {
    public String name;
    public ArrayList<ModelMusic> List_MusicOfPlaylist;

    public ModelPlaylist() {
        List_MusicOfPlaylist = new ArrayList<ModelMusic>();
    }
}