package com.example.musiclibrary.init;
import com.example.musiclibrary.dtos.PlayListDto;
import com.example.musiclibrary.dtos.TrackDto;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private PlayListService playListService;
    @Autowired
    private TrackListService trackListService;
    @Autowired
    private SubscriptionService subscriptionService;
    public CommandLineRunnerImpl(UserService userService, TrackService trackService, PlayListService playListService, TrackListService trackListService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.trackService = trackService;
        this.playListService = playListService;
        this.trackListService = trackListService;
        this.subscriptionService = subscriptionService;
    }
    @Override
    public void run(String... args) throws Exception {
        seedData();
    }
    private void seedData() throws IOException {
        UserDto u1 = new UserDto("test_user_1", "12345");
        UserDto u2 = new UserDto("test_user_2", "12345");
        UserDto alt_u = new UserDto("test_user_3", "12345");
        TrackDto t1 = new TrackDto("Zazie", "Kevin Macleod", "Ferret", "03:34", "Electronic");
        TrackDto t2 = new TrackDto("Freestyler", "Bomfunk MC's", "In Stereo", "05:06", "Hip Hop");
        TrackDto alt_t = new TrackDto("Corrosion", "NightHawk22", "Chemical Imbalance", "03:24", "Techno");
        PlayListDto p1 = new PlayListDto("Плейлист 1", u1);
        PlayListDto p2 = new PlayListDto("Плейлист 2", u2);
        PlayListDto p3 = new PlayListDto("Плейлист 3", u2);
        PlayListDto alt_p = new PlayListDto("Changed", u1);
        u1 = userService.register(u1);
        u2 = userService.register(u2);
        t1 = trackService.addTrack(t1);
        t2 = trackService.addTrack(t2);
        userService
                .getAllUsers()
                .forEach(System.out::println);
        trackService
                .getAllTracks()
                .forEach(System.out::println);
        p1 = playListService.createPlaylist(p1, "test_user_1");
        p2 = playListService.createPlaylist(p2, "test_user_2");
        p3 = playListService.createPlaylist(p3, "test_user_2");
        playListService
                .getAllPlaylists()
                .forEach(System.out::println);
        System.out.println(userService.findUser("test_user_1"));
        trackService.changeTrackInfo("Freestyler", "Bomfunk MC's", alt_t);
        trackListService.addTrackToPlaylist("test_user_1","Плейлист 1", "Zazie", "Kevin Macleod");
        trackListService.addTrackToPlaylist("test_user_2","Плейлист 2", "Corrosion", "NightHawk22");
        subscriptionService.addSub("test_user_1", "Плейлист 1", "test_user_1");
        subscriptionService.addSub("test_user_1", "Плейлист 2", "test_user_2");
        subscriptionService.addSub("test_user_1", "Плейлист 2", "test_user_2");
        subscriptionService.addSub("test_user_1", "Плейлист 3", "test_user_2");
        System.out.println(playListService.getAllPlaylistsByUser("test_user_2"));
        System.out.println(subscriptionService.getAllSubsByUser("test_user_1"));
        System.out.println(trackListService.getAllTracksFromPlaylist("test_user_1", "Плейлист 1"));
    }
}