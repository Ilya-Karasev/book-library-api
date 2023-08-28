package com.example.musiclibrary.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
@Entity
@Table(name = "subscription")
public class Subscription extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "playlist_id", referencedColumnName = "id", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private PlayList playlist;
    protected Subscription(){
    }
    public Subscription(User user, PlayList playlist) {
        this.user = user;
        this.playlist = playlist;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public PlayList getPlayList() {
        return playlist;
    }
    public void setPlayList(PlayList playlist) {
        this.playlist = playlist;
    }
    @Override
    public String toString() {
        return "Subscription { id= " + id + ", User=" + user + ", playlist=" + playlist + " }";
    }
}