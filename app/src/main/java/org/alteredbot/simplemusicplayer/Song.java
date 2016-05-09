package org.alteredbot.simplemusicplayer;

public class Song {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPictureHolder() {
        return pictureHolder;
    }

    public void setPictureHolder(int pictureHolder) {
        this.pictureHolder = pictureHolder;
    }

    public String getMusicHolder() {return musicHolder;}
    public void setMusicHolder(String musicHolder){this.musicHolder = musicHolder;}

    String title;
    String composer;
    String description;
    int pictureHolder;
    String musicHolder;


    public Song(String title, String composer, String description, int pictureHolder, String musicHolder){
        this.title = title;
        this.composer = composer;
        this.description = description;
        this.pictureHolder = pictureHolder;
        this.musicHolder = musicHolder;
    }




}
