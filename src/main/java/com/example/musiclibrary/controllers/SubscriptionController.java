package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.SubscriptionDto;
import com.example.musiclibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    @GetMapping("/mySubs/{username}")
    List<? extends SubscriptionDto> allSubsByUser(@PathVariable String username) {
        return subscriptionService.getAllSubsByUser(username);
    }
    @PostMapping("/mySubs/{username}/addSub/{pl_name}/{owner}")
    SubscriptionDto addSub(@PathVariable String username, @PathVariable String pl_name, @PathVariable String owner) {  return subscriptionService.addSub(username, pl_name, owner); }
    @GetMapping("/mySubs/{username}/{pl_name}")
    SubscriptionDto findPlaylistInSubs(@PathVariable String username, @PathVariable String pl_name) throws Throwable {
        return (SubscriptionDto) subscriptionService.findPlaylistInSubs(username, pl_name).orElseThrow(() -> new NotFoundException(pl_name));
    }
    @DeleteMapping("/mySubs/{username}/unsub/{pl_name}/{owner}")
    void deleteSub(@PathVariable String username, @PathVariable String pl_name, @PathVariable String owner) {
        subscriptionService.unSub(username, pl_name, owner);
    }
}