package com.example.musiclibrary.services;
import com.example.musiclibrary.dtos.SubscriptionDto;
import java.util.List;
import java.util.Optional;
public interface SubscriptionService<ID> {
    SubscriptionDto addSub(String username, String pl_name, String owner);
    Optional<SubscriptionDto> findPlaylistInSubs(String username, String pl_name);
    List<SubscriptionDto> getAllSubsByUser(String username);
    void unSub(String username, String pl_name, String owner);
}