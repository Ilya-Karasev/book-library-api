package com.example.musiclibrary.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    private String pass;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    protected Set<PlayList> playList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    protected Set<Subscription> subscription;
    public User(String username, String pass) {
        this.username = username;
        this.pass = pass;
        playList = new HashSet<PlayList>();
        subscription = new HashSet<Subscription>();
    }
    protected User(){
    }
    @Column(name = "UserName", length = 50, nullable = false)
    public String getUsername() {
        return username;
    }
    @Column(name = "Password", length = 50, nullable = false)
    public String getPassword() {
        return pass;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    private void setPassword(String pass) {
        this.pass = pass;
    }
    @Override
    public String toString() {
        return "User { id=" + id + ", user_name=" + username + ", password=" + pass + " }";
    }
}