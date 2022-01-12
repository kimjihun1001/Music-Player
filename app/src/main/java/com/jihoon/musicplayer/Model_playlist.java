package com.jihoon.musicplayer;

import java.util.ArrayList;

public class Model_playlist {
    public String name;
    public ArrayList<Model_music> listOfMusic;

    public Model_playlist() {
        listOfMusic = new ArrayList<Model_music>();
    }
}