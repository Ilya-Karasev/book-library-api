package com.example.musiclibrary.dtos;
public class PlayListDto {
    private int id;
    private String playlistname;
    private UserDto user;
    public PlayListDto(String playlistname, UserDto user) {
        this.playlistname = playlistname;
        this.user = user;
    }
    public PlayListDto() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPlayListName() {
        return playlistname;
    }
    public void setPlayListName(String playlistname) {
        this.playlistname = playlistname;
    }
    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "PlayList { id=" + id + ", playlist_name=" + playlistname + ", owner=" + user.getUsername() + " }";
    }
}
