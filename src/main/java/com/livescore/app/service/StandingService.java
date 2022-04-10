package com.livescore.app.service;


import com.livescore.app.model.StandingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandingService {

    @Autowired
    ApiService apiService;


    public StandingData getStandingByStageId(Integer id, String expand){


        return apiService.getStandingByStageId(id, expand);
    }
}
