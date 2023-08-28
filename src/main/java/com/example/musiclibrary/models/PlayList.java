package com.example.musiclibrary.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.Set;
@Entity
@Table(name = "playlists")
public class PlayList extends BaseEntity {
    private String playlistname;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    private Set<Subscription> subscription;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    private Set<TrackList> trackList;
    public PlayList(String playlistname) {
        this.playlistname = playlistname;
    }
    protected PlayList(){
    }
    @Column(name = "playlist_name", length = 250, nullable = false)
    public String getPlayListName() {
        return playlistname;
    }
    private void setPlayListName(String playlistname) {
        this.playlistname = playlistname;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "PlayList { id=" + id + ", playlist_name=" + playlistname + ", owner=" + user.getUsername() + " }";
    }
}