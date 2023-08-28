package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.TrackDto;
import com.example.musiclibrary.models.Track;
import com.example.musiclibrary.repositories.TrackRepository;
import com.example.musiclibrary.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TrackServiceImpl implements TrackService<Integer> {
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TrackDto addTrack(TrackDto track) {
        if (!trackRepository.existsByTracknameAndArtist(track.getTrackName(), track.getArtist())) {
        Track t = modelMapper.map(track, Track.class);
        return modelMapper.map(trackRepository.save(t), TrackDto.class);
        } else return null;
    }
    @Override
    public Optional<TrackDto> findTrack(String trackname, String artist) {
        return Optional.ofNullable(modelMapper.map(trackRepository.findByTracknameAndArtist(trackname, artist), TrackDto.class));
    }
    @Override
    public List<TrackDto> findTrackByTrackname(String trackname) {
        return trackRepository.findByTrackname(trackname).stream().map((t) -> modelMapper.map(t, TrackDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<TrackDto> findTrackByArtist(String artist) {
        return trackRepository.findByArtist(artist).stream().map((t) -> modelMapper.map(t, TrackDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<TrackDto> findTrackByAlbum(String album) {
        return trackRepository.findByAlbum(album).stream().map((t) -> modelMapper.map(t, TrackDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<TrackDto> findTrackByGenre(String genre) {
        return trackRepository.findByGenre(genre).stream().map((t) -> modelMapper.map(t, TrackDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<TrackDto> getAllTracks() {
        return trackRepository.findAll().stream().map((t) -> modelMapper.map(t, TrackDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<TrackDto> changeTrackInfo(String trackname, String artist, TrackDto track) {
        TrackDto t = modelMapper.map(trackRepository.findByTracknameAndArtist(trackname, artist), TrackDto.class);
        t.setArtist(track.getArtist());
        t.setTrackName(track.getTrackName());
        t.setAlbum(track.getAlbum());
        t.setDuration(track.getDuration());
        t.setGenre(track.getGenre());
        trackRepository.save(modelMapper.map(t, Track.class));
        return Optional.ofNullable(modelMapper.map(trackRepository.findByTrackname(track.getTrackName()), TrackDto.class));
    }
    @Override
    public void deleteTrack(String trackname, String artist) {
        trackRepository.delete(modelMapper.map(trackRepository.findByTracknameAndArtist(trackname, artist), Track.class));
    }
}