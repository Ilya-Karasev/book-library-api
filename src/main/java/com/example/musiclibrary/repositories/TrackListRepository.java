package com.example.musiclibrary.repositories;
import com.example.musiclibrary.models.PlayList;
import com.example.musiclibrary.models.Track;
import com.example.musiclibrary.models.TrackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface TrackListRepository extends JpaRepository<TrackList, Integer> {
    boolean existsByTrackIdAndPlaylistId(Integer track_id, Integer pl_id);
    @Query(value = "select t from TrackList t where t.playlist in (select p1 from PlayList p1 join p1.user u1 where p1.id in (select p2.id from PlayList p2 join p2.user u2 where u2.username=:username and p2.playlistname=:pl_name))")
    List<TrackList> findByUserUsernameAndPlaylistPlaylistname(@Param(value = "username") String username, @Param(value = "pl_name") String pl_name);
    Optional<TrackList> findByPlaylistPlaylistnameAndTrackTrackname(String pl_name, String trackname);
    List<TrackList> findByPlaylist(PlayList playList);
    Optional<TrackList> findByTrackAndPlaylist(Track track, PlayList playList);
}