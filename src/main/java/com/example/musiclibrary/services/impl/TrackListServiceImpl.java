package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.TrackListDto;
import com.example.musiclibrary.models.*;
import com.example.musiclibrary.repositories.*;
import com.example.musiclibrary.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TrackListServiceImpl implements TrackListService<Integer> {
    @Autowired
    private TrackListRepository trackListRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TrackListDto addTrackToPlaylist(String username, String pl_name, String trackname, String artist) {
        Track t = trackRepository.findByTracknameAndArtist(trackname, artist).get();
        PlayList p = playListRepository.findByUserUsernameAndPlaylistname(username, pl_name).get();
        if (!trackListRepository.existsByTrackIdAndPlaylistId(t.getId(), p.getId())) {
            TrackList tl = new TrackList(t, p);
            return modelMapper.map(trackListRepository.save(tl), TrackListDto.class);
        } else return null;
    }
    @Override
    public Optional<TrackListDto> findTrackInPlaylist(String username, String pl_name, String trackname, String artist) {
        PlayList p = playListRepository.findByUserUsernameAndPlaylistname(username, pl_name).get();
        Track t = trackRepository.findByTracknameAndArtist(trackname, artist).get();
        Integer tlid = trackListRepository.findByTrackAndPlaylist(t, p).get().getId();
        return Optional.ofNullable(modelMapper.map(trackListRepository.findByTrackAndPlaylist(t, p), TrackListDto.class));
    }
    @Override
    public List<TrackListDto> getAllTracksFromPlaylist(String username, String pl_name) {
        return trackListRepository.findByUserUsernameAndPlaylistPlaylistname(username, pl_name).stream().map((tl) -> modelMapper.map(tl, TrackListDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<TrackListDto> findTrackInPlaylistByUser(String username, String pl_name, String trackname, String artist) {
        PlayList p = playListRepository.findByUserUsernameAndPlaylistname(username, pl_name).get();
        Track t = trackRepository.findByTracknameAndArtist(trackname, artist).get();
        Integer tlid = trackListRepository.findByTrackAndPlaylist(t, p).get().getId();
        return Optional.ofNullable(modelMapper.map(trackListRepository.findById(tlid), TrackListDto.class));
    }
    @Override
    public List<TrackListDto> getAllTracksFromPlaylistByUser(String username, String pl_name) {
        PlayList p = playListRepository.findByUserUsernameAndPlaylistname(username, pl_name).get();
        return trackListRepository.findByPlaylist(p).stream().map((tl) -> modelMapper.map(tl, TrackListDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<TrackListDto> findTrackInSubByUser(String username, String pl_name, String trackname, String artist) {
        PlayList p = subscriptionRepository.findByUserUsernameAndPlaylistPlaylistname(username, pl_name).get().getPlayList();
        Track t = trackRepository.findByTracknameAndArtist(trackname, artist).get();
        Integer tlid = trackListRepository.findByTrackAndPlaylist(t, p).get().getId();
        return Optional.ofNullable(modelMapper.map(trackListRepository.findById(tlid), TrackListDto.class));
    }
    @Override
    public List<TrackListDto> getAllTracksFromSubByUser(String username, String pl_name) {
        PlayList p = subscriptionRepository.findByUserUsernameAndPlaylistPlaylistname(username, pl_name).get().getPlayList();
        return trackListRepository.findByPlaylist(p).stream().map((tl) -> modelMapper.map(tl, TrackListDto.class)).collect(Collectors.toList());
    }
    @Override
    public void deleteTrackFromPlaylist(String username, String pl_name, String trackname, String artist) {
        PlayList p = playListRepository.findByUserUsernameAndPlaylistname(username, pl_name).get();
        Track t = trackRepository.findByTracknameAndArtist(trackname, artist).get();
        Integer tid = trackListRepository.findByTrackAndPlaylist(t, p).get().getId();
        trackListRepository.delete(modelMapper.map(trackListRepository.findById(tid), TrackList.class));
    }
}