package com.example.musiclibrary.dtos;
public class TrackListDto {
    private int id;
    private PlayListDto playlist;
    private TrackDto track;
    public TrackListDto(PlayListDto playlist, TrackDto track) {
        this.playlist = playlist;
        this.track = track;
    }
    public TrackListDto() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public PlayListDto getPlayList() {
        return playlist;
    }
    public void setPlayList(PlayListDto playlist) {
        this.playlist = playlist;
    }
    public TrackDto getTrack() {
        return track;
    }
    public void setTrack(TrackDto track) {
        this.track = track;
    }
    @Override
    public String toString() {
        return "TrackList { id=" + id + ", playlist=" + playlist + ", track=" + track + " }";
    }
}