package com.example.musiclibrary.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "tracks")
public class Track extends BaseEntity {
    private String trackname;
    private String artist;
    private String album;
    private String duration;
    private String genre;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "track")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    protected Set<TrackList> trackList;
    public Track(String trackname) {
        this.trackname = trackname;
        trackList = new HashSet<TrackList>();
    }
    protected Track(){
    }
    @Column(name = "TrackName", length = 50, nullable = false)
    public String getTrackName() {
        return trackname;
    }
    @Column(name = "Artist", length = 50, nullable = false)
    public String getArtist() {
        return artist;
    }
    @Column(name = "Album", length = 50, nullable = false)
    public String getAlbum() {
        return album;
    }
    @Column(name = "Duration", length = 50, nullable = false)
    public String getDuration() {
        return duration;
    }
    @Column(name = "Genre", length = 50, nullable = false)
    public String getGenre() {
        return genre;
    }
    private void setTrackName(String trackname) {
        this.trackname = trackname;
    }
    private void setArtist(String artist) {
        this.artist = artist;
    }
    private void setAlbum(String album) {
        this.album = album;
    }
    private void setDuration(String duration) {
        this.duration = duration;
    }
    private void setGenre(String genre) {
        this.genre = genre;
    }
    @Override
    public String toString() {
        return "Track { id=" + id + ", track_name=" + trackname + ", artist=" + artist +
                ", album=" + album + ", duration=" + duration + ", genre=" + genre +" }";
    }
}