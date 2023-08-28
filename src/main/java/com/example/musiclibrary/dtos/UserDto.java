package com.example.musiclibrary.dtos;
public class UserDto {
    private int id;
    private String username;
    private String pass;
    public UserDto(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }
    public UserDto() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return pass;
    }
    public void setPassword(String pass) {
        this.pass = pass;
    }
    @Override
    public String toString() {
        return "User { id=" + id + ", user_name=" + username + ", password=" + pass + " }";
    }
}