package com.livescore.app.elenaservices;


import com.livescore.app.elenamodel.LeagueResponse;
import com.livescore.app.elenamodel.StandingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandingService {

    @Autowired
    ApiService apiService;

    public StandingData getStandingByStageId(Integer id){

        return apiService.getStandingByStageId(id);
    }

    public LeagueResponse getLeagueStandingByStageId(Integer id){

        return apiService.getLeagueAndStandingByStageId(id).getData().get(0).getExpand()
               .getStage().get(0).getExpand().getSeason().get(0).getExpand().getLeague().get(0);
    }
}