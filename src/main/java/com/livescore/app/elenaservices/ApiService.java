package com.livescore.app.elenaservices;

import com.livescore.app.elenamodel.*;
import com.livescore.app.newsmodel.NewsResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

public class ApiService {

    @Value("${league_url}")
    private String leagueUrl;

    @Value("${season_url}")
    private String seasonUrl;

    @Value("${stage_url}")
    private String stageUrl;

    @Value("${player_url}")
    private String playerUrl;

    @Value("${fixtures_url}")
    private String fixtureUrl;

    @Value("${BaseFixtureUrl}")
    private String BaseFixtureUrl;

    @Value("${venue_url}")
    private String venueUrl;

    @Value("${team_url}")
    private String teamUrl;

    @Value("${country_url}")
    private String countryUrl;

    @Value("${news_api_key}")
    private String newsApiKey;


    @Autowired
    RestTemplate restTemplate;

    final private String token;

    public ApiService(String token) {
        this.token = token;
    }

    public LeagueData getLeagueById(Integer id, String expand) {

        String url = leagueUrl + id;
        String url2 = leagueUrl + id + "?expand={expand}";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("expand", expand);

        ResponseEntity<LeagueData> response;
        if (expand == null) {

            response = restTemplate.exchange(url, HttpMethod.GET, request, LeagueData.class);
        } else {

            response = restTemplate.exchange(url2, HttpMethod.GET, request, LeagueData.class, uriParams);
        }
        return response.getBody();

    }

    public SeasonData getSeasonByLeagueId(Integer id) {

        String url = seasonUrl + id + "/seasons" + "?expand={expand}";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

//        Map<String, String> uriParams = new HashMap<>();
//        uriParams.put("expand",);

        ResponseEntity<SeasonData> response = restTemplate.exchange(url, HttpMethod.GET, request, SeasonData.class);

        return response.getBody();
    }


    public StageData getStageBySeasonId(Integer id) {

        String url = seasonUrl + id + "/stages";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<StageData> response = restTemplate.exchange(url, HttpMethod.GET, request, StageData.class);

        return response.getBody();


    }

    public TopScorerData getTopScorerBySeasonId(Integer id) {


        String url = playerUrl + id + "/topscorers";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<TopScorerData> response = restTemplate.exchange(url, HttpMethod.GET, request, TopScorerData.class);

        return response.getBody();


    }


    public PlayerData getTopAppearancesBySeasonId(Integer id) {


        String url = playerUrl + id + "/topappearances";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<PlayerData> response = restTemplate.exchange(url, HttpMethod.GET, request, PlayerData.class);

        return response.getBody();


    }

    public EventData getEventByFixtureId(Integer id) {

        String url = fixtureUrl + id + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<EventData> response = restTemplate.exchange(url, HttpMethod.GET, request, EventData.class);

        return response.getBody();

    }


    public List<LineUpResponse> getLineUpByFixtureId(Integer id) {

        int page = 1;

        boolean hasNextPage = false;

        List<LineUpResponse> lineUps = new ArrayList<>();


        do {

            String url = fixtureUrl + id + "/lineups?page=" + page;

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            headers.setBearerAuth(token);

            ResponseEntity<LineUpData> response = restTemplate.exchange(url, HttpMethod.GET, request, LineUpData.class);
            if(response.getBody() != null){
                lineUps.addAll(response.getBody().getData());
                hasNextPage = response.getBody().getPagination().getHasNextPage();
            }
            page = page + 1;

        } while (hasNextPage);


        return lineUps;

    }


    public PlayerDetailData getPlayerDetailBySeasonId(Integer id, String expand) {

        String url = seasonUrl + id + "/players";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<PlayerDetailData> response = restTemplate.exchange(url, HttpMethod.GET, request, PlayerDetailData.class);

        return response.getBody();
    }


    public StandingData getStandingByStageId(Integer id) {

        String url2 = stageUrl + id + "/standing" + "?expand=team";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<StandingData> response = restTemplate.exchange(url2, HttpMethod.GET, request, StandingData.class);
        return response.getBody();

    }

    public StandingData getLeagueAndStandingByStageId(Integer id) {

        String url2 = stageUrl + id + "/standing" + "?expand=stage.season.league";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<StandingData> response = restTemplate.exchange(url2, HttpMethod.GET, request, StandingData.class);
        return response.getBody();

    }


    public VenueData getVenueById(Integer id) {

        String url = venueUrl + id;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<VenueData> response = restTemplate.exchange(url, HttpMethod.GET, request, VenueData.class);

        return response.getBody();
    }

    public TeamData getTeamById(Integer id) {

        String url = teamUrl + id;


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);


        ResponseEntity<TeamData> response = restTemplate.exchange(url, HttpMethod.GET, request, TeamData.class);

        return response.getBody();
    }


    public FixtureData getFixturesBySeasonId(Integer id, String date) {


        Date date1 = new Date();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(date1);

        String url2 = seasonUrl + id + "/fixtures" + "?from=" + dateNow;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);


        ResponseEntity<FixtureData> response;

        response = restTemplate.exchange(url2, HttpMethod.GET, request, FixtureData.class);
        return response.getBody();

    }


    public FixtureData getFixtureById(Integer id) {

        String url = fixtureUrl + id;


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);

        return response.getBody();

    }

    public FixtureData getFixtureHomeTeamById(Integer id) {

        String url = fixtureUrl + id + "?expand=home_team";


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);

        return response.getBody();

    }

    public FixtureData getFixtureAwayTeamById(Integer id) {

        String url = fixtureUrl + id + "?expand=away_team";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);

        return response.getBody();

    }


    public FixtureData getFixtureLeagueById(Integer id) {

        String url = fixtureUrl + id + "?expand=league.country";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);

        return response.getBody();
    }


    public StatData getStatsByFixtureId(Integer id) {


        String url = fixtureUrl + id + "/stats";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<StatData> response = restTemplate.exchange(url, HttpMethod.GET, request, StatData.class);

        return response.getBody();


    }

    public FixtureData getFixtureHeadToHead(Integer id) {

        String url = fixtureUrl + id + "?expand=h2h";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);

        return response.getBody();
    }

    public CountryData getCountries() {

        String url = countryUrl;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<CountryData> response = restTemplate.exchange(url, HttpMethod.GET, request, CountryData.class);

        return response.getBody();

    }

    public LeagueData getNextFixturesByLeagueId(Integer id) {


        String url = leagueUrl + id + "?expand=seasons.next_fixtures";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<LeagueData> response = restTemplate.exchange(url, HttpMethod.GET, request, LeagueData.class);

        return response.getBody();
    }

    public LeagueData getHomeNextFixturesByLeagueId(Integer id) {

        String url = leagueUrl + id + "?expand=seasons.next_fixtures.home_team";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<LeagueData> response = restTemplate.exchange(url, HttpMethod.GET, request, LeagueData.class);

        return response.getBody();

    }

    public LeagueData getAwayNextFixturesByLeagueId(Integer id) {


        String url = leagueUrl + id + "?expand=seasons.next_fixtures.away_team";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        headers.setBearerAuth(token);

        ResponseEntity<LeagueData> response = restTemplate.exchange(url, HttpMethod.GET, request, LeagueData.class);

        return response.getBody();

    }



    public NewsResponses getNewsArticles() {

        Date date1 = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(date1);

        String url = "https://api.newscatcherapi.com/v2/search?q=European football&lang=en&countries=GB&topic=sport";


        HttpHeaders headers = new HttpHeaders();
        headers.add("x_api_key", newsApiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<NewsResponses> response = restTemplate.exchange(url, HttpMethod.GET, request, NewsResponses.class);

        return response.getBody();

    }

    public NewsResponses getTeamNewsArticles(String team) {


        String url = "https://api.newscatcherapi.com/v2/search?q=" + team + "&topic=sport";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x_api_key", newsApiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<NewsResponses> response = restTemplate.exchange(url, HttpMethod.GET, request, NewsResponses.class);

        return response.getBody();

    }

    public NewsResponses getLatestArticles() {


        String url = "https://api.newscatcherapi.com/v2/latest_headlines?countries=GB&topic=sport&page_size=100";

        HttpHeaders headers = new HttpHeaders();
        headers.add("x_api_key", newsApiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<NewsResponses> response = restTemplate.exchange(url, HttpMethod.GET, request, NewsResponses.class);

        return response.getBody();

    }

    public List<FixturesResponse> getResultsByTeamId(Integer id) {

        int page = 1;

        boolean hasNextPage = false;

        List<FixturesResponse> fixtures = new ArrayList<>();

        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(date1);


        do {

            String url = BaseFixtureUrl + "?from=2021-08-01"+"&idTeam1="+ id + "&to=" + date + "&page="+page;

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            headers.setBearerAuth(token);

            ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);
            if(response.getBody() != null){
                fixtures.addAll(response.getBody().getData());
                hasNextPage = response.getBody().getPagination().getHasNextPage();
            }
            page = page + 1;

        } while (hasNextPage);


        return fixtures;

    }

    public List<FixturesResponse> getFixturesByTeamId(Integer id) {

        int page = 1;

        boolean hasNextPage = false;

        List<FixturesResponse> fixtures = new ArrayList<>();

        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(date1);


        do {

            String url = BaseFixtureUrl + "?from=" + date +"&idTeam1="+ id +"&page="+page;


            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            headers.setBearerAuth(token);

            ResponseEntity<FixtureData> response = restTemplate.exchange(url, HttpMethod.GET, request, FixtureData.class);
            if(response.getBody() != null){
                fixtures.addAll(response.getBody().getData());
                hasNextPage = response.getBody().getPagination().getHasNextPage();
            }
            page = page + 1;

        } while (hasNextPage);

        return fixtures;

    }
}

