package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.SubscriptionDto;
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
public class SubscriptionServiceImpl implements SubscriptionService<Integer> {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public SubscriptionDto addSub(String username, String pl_name, String owner) {
        if ((userRepository.findByUsername(username).get().getId()) != (playListRepository.findByUserUsernameAndPlaylistname(owner, pl_name).get().getUser().getId())) {
            User u = userRepository.findByUsername(username).get();
            PlayList p = playListRepository.findByUserUsernameAndPlaylistname(owner, pl_name).get();
            if (!subscriptionRepository.existsByUserIdAndPlaylistId(u.getId(), p.getId())) {
                Subscription s = new Subscription(u, p);
                return modelMapper.map(subscriptionRepository.save(s), SubscriptionDto.class);
                }
            else return null;
            }
        else return null;
    }
    @Override
    public Optional<SubscriptionDto> findPlaylistInSubs(String username, String pl_name) {
        return Optional.ofNullable(modelMapper.map(subscriptionRepository.findByUserUsernameAndPlaylistPlaylistname(username, pl_name), SubscriptionDto.class));
    }
    @Override
    public List<SubscriptionDto> getAllSubsByUser(String username) {
        return subscriptionRepository.findByUserUsername(username).stream().map((s) -> modelMapper.map(s, SubscriptionDto.class)).collect(Collectors.toList());
    }
    @Override
    public void unSub(String username, String pl_name, String owner) {
        User u = userRepository.findByUsername(username).get();
        PlayList p = playListRepository.findByUserUsernameAndPlaylistname(owner, pl_name).get();
        Integer sid = subscriptionRepository.findByUserAndPlaylist(u, p).get().getId();
        subscriptionRepository.delete(modelMapper.map(subscriptionRepository.findById(sid), Subscription.class));
    }
}