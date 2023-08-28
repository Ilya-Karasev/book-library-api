package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.PlayListDto;
import com.example.musiclibrary.models.PlayList;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.repositories.PlayListRepository;
import com.example.musiclibrary.repositories.UserRepository;
import com.example.musiclibrary.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PlayListServiceImpl implements PlayListService<Integer> {
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PlayListDto createPlaylist(PlayListDto playlist, String username) {
        if (!playListRepository.existsByUserUsernameAndPlaylistname(username, playlist.getPlayListName())) {
            PlayList p = modelMapper.map(playlist, PlayList.class);
            User u = userRepository.findByUsername(username).get();
            p.setUser(u);
            return modelMapper.map(playListRepository.save(p), PlayListDto.class);
        } else return null;
    }
    @Override
    public List<PlayListDto> findPlaylist(String pl_name) {
        return playListRepository.findByPlaylistname(pl_name).stream().map((p) -> modelMapper.map(p, PlayListDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<PlayListDto> findPlaylistByUser(String username, String pl_name) {
        return Optional.ofNullable(modelMapper.map(playListRepository.findByUserUsernameAndPlaylistname(username, pl_name), PlayListDto.class));
    }
    @Override
    public List<PlayListDto> getAllPlaylists() {
        return playListRepository.findAll().stream().map((p) -> modelMapper.map(p, PlayListDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<PlayListDto> getAllPlaylistsByUser(String username) {
        return playListRepository.findByUserUsername(username).stream().map((p) -> modelMapper.map(p, PlayListDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<PlayListDto> changePlaylistInfo(String username, String pl_name, PlayListDto playlist) {
        PlayListDto p = modelMapper.map(playListRepository.findByUserUsernameAndPlaylistname(username, pl_name), PlayListDto.class);
        p.setPlayListName(playlist.getPlayListName());
        playListRepository.save(modelMapper.map(p, PlayList.class));
        return Optional.ofNullable(modelMapper.map(playListRepository.findByUserUsernameAndPlaylistname(username, playlist.getPlayListName()), PlayListDto.class));
    }
    @Override
    public void deletePlaylist(String username, String pl_name) {
        playListRepository.delete(modelMapper.map(playListRepository.findByUserUsernameAndPlaylistname(username, pl_name), PlayList.class));
    }
}