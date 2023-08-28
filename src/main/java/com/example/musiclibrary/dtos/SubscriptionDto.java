package com.example.musiclibrary.dtos;
public class SubscriptionDto {
    private int id;
    private UserDto user;
    private PlayListDto playlist;
    public SubscriptionDto(UserDto user, PlayListDto playlist) {
        this.user = user;
        this.playlist = playlist;
    }
    public SubscriptionDto() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user = user;
    }
    public PlayListDto getPlayList() {
        return playlist;
    }
    public void setPlayList(PlayListDto playlist) {
        this.playlist = playlist;
    }
    @Override
    public String toString() {
        return "Subscription { id=" + id + ", User=" + user + ", PlayList=" + playlist +" }";
    }
}