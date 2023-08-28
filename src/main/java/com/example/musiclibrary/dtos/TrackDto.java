package com.example.musiclibrary.dtos;
public class TrackDto {
    private int id;
    private String trackname;
    private String artist;
    private String album;
    private String duration;
    private String genre;
    public TrackDto(String trackname, String artist, String album, String duration, String genre) {
        this.trackname = trackname;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
    }
    public TrackDto() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTrackName() {
        return trackname;
    }
    public void setTrackName(String trackname) {
        this.trackname = trackname;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
            this.duration = duration;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    @Override
    public String toString() {
        return "Track { id=" + id + ", track_name=" + trackname + ", artist=" + artist +
                ", album=" + album + ", duration=" + duration + ", genre=" + genre + " }";
    }
}
