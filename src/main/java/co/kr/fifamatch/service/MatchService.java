package co.kr.fifamatch.service;

import co.kr.fifamatch.client.ApiClient;
import co.kr.fifamatch.model.dto.MatchDto;
import co.kr.fifamatch.model.vo.MatchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final ApiClient apiClient;

    public String getUsers(String nickname){
        return apiClient.getUser(nickname);
    }

    public List<String> findMatches(MatchDto matchDto, String accessId) {
        return apiClient.findMatches(matchDto, accessId);
    }

    public MatchVo calculateWDL(List<String> matchList, MatchDto matchDto) {
        String opponentId = getUsers(matchDto.getNickname2());

        return apiClient.calculateWDL(matchList, matchDto, opponentId);
    }


}
