package co.kr.fifamatch.client;

import co.kr.fifamatch.model.dto.MatchDto;
import co.kr.fifamatch.model.vo.MatchVo;
import co.kr.fifamatch.model.vo.ScoreVo;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiClient {
    @Value("${app.api.url}")
    private String url;

    @Value("${app.api.key}")
    private String key;

    public String getUser(String nickname) {
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        httpHeaders.set("Authorization", key);

        HttpEntity entity = new HttpEntity(httpHeaders);

        String requestUrl = UriComponentsBuilder.fromHttpUrl(url + "users")
                .queryParam("nickname", nickname)
                .build()
                .toUriString();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            return jsonObject.get("accessId") == null ? null : jsonObject.get("accessId").toString();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> findMatches(MatchDto matchDto, String accessId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        httpHeaders.set("Authorization", key);
        HttpEntity entity = new HttpEntity(httpHeaders);

        List<String> list = new ArrayList<>();

        int a = matchDto.getMatchCount() / 100;
        int b = matchDto.getMatchCount() % 100;

        for (int i=0; i <= a ; i++) {
            String requestUrl = UriComponentsBuilder.fromHttpUrl(url + "users/" + accessId + "/matches")
                    .queryParam("acessid", accessId)
                    .queryParam("matchtype", matchDto.getMatchType())
                    .queryParam("offset", i)
                    .queryParam("limit", i == a ? b : 100)
                    .build()
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

            JSONArray jsonArray = new JSONArray(responseEntity.getBody());

            if (jsonArray != null){

            }

            for (int k=0; k < jsonArray.length(); k++) {
                list.add(jsonArray.get(k).toString());
            }

        }

        return list;
    }

    public MatchVo calculateWDL(List<String> matchList, MatchDto matchDto, String accessId2) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        httpHeaders.set("Authorization", key);
        HttpEntity entity = new HttpEntity(httpHeaders);

        MatchVo matchVo = new MatchVo(0, 0, 0,  matchDto.getNickname1(), matchDto.getNickname2(), new ArrayList<>());
        for(String s : matchList) {
            String requestUrl = UriComponentsBuilder.fromHttpUrl(url + "matches/" + s)
                    .build()
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

            JSONObject jsonObject = new JSONObject(responseEntity.getBody());

            String opponent = jsonObject.getJSONArray("matchInfo").getJSONObject(1).get("accessId").toString();
            String WDL = jsonObject.getJSONArray("matchInfo").getJSONObject(0).getJSONObject("matchDetail").get("matchResult").toString();
            String homeGoalTotal = jsonObject.getJSONArray("matchInfo").getJSONObject(0).getJSONObject("shoot").get("goalTotal").toString();
            String awayGoalTotal = jsonObject.getJSONArray("matchInfo").getJSONObject(1).getJSONObject("shoot").get("goalTotal").toString();
            String matchDate = jsonObject.get("matchDate").toString();

            if (opponent.equals(accessId2)) {
                matchVo.set(WDL, new ScoreVo(homeGoalTotal, awayGoalTotal, matchDate));
            }

        }

        return matchVo;
    }
}
