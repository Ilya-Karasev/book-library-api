package com.example.musiclibrary.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
@Entity
@Table(name = "tracklist")
public class TrackList extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "playlist_id", referencedColumnName = "id", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    protected PlayList playlist;
    @ManyToOne(optional = false)
    @JoinColumn(name = "track_id", referencedColumnName = "id", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    protected Track track;
    protected TrackList() {
    }
    public TrackList(Track track, PlayList playlist){
        this.track = track;
        this.playlist = playlist;
    }
    public PlayList getPlayLists() {
        return playlist;
    }
    public void setPlayList(PlayList playlist) {
        this.playlist = playlist;
    }
    public Track getTrack() {
        return track;
    }
    public void setTrack(Track track) {
        this.track = track;
    }
    @Override
    public String toString() {
        return "TrackList { id=" + id + ", PlayList=" + playlist + ", Track=" + track + " }";
    }
}