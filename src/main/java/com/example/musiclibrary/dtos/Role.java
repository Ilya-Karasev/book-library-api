package com.example.musiclibrary.dtos;

public enum Role {
    User(0), Librarian(1);
    private final int roleNum;
    Role(int roleNum) {
        this.roleNum = roleNum;
    }
    public int getRoleNum() {
        return roleNum;
    }
}
