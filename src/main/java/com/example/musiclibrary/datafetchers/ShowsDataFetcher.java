package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.Show;
import com.example.musiclibrary.datafetchers.records.SubmittedShow;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDataFetcher {
    private List<Show> shows = new ArrayList<>(List.of(
            new Show("Stranger Things", 2016),
            new Show("Ozark", 2017),
            new Show("The Crown", 2016),
            new Show("Dead to Me", 2019),
            new Show("Orange is the New Black", 2013)
    ));
    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if(titleFilter == null) {
            return shows;
        }
        return shows.stream().filter(s -> s.title().contains(titleFilter)).collect(Collectors.toList());
    }
    @DgsMutation
    public Show addShow(@InputArgument SubmittedShow show) {
        Show newShow = new Show(show.title(),show.releaseYear());
        shows.add(newShow);
        return newShow;
    }

}