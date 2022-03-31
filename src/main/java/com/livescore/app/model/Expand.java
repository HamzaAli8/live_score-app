package com.livescore.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Expand {

    private List<Country> country;
    @JsonProperty("current_season")
    private List<Seasons> currentSeason;
    @JsonProperty("next_fixtures")
    private List<Fixtures> nextFixtures;


}
