package co.kr.fifamatch.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
public class MatchVo {
    private Integer wCount;
    private Integer lCount;
    private Integer dCount;

    private String homeUser;

    private String awayUser;
    private List<ScoreVo> scoreVoList;

    public void set(String WDL, ScoreVo scoreVo) {
        if (WDL.equals("승"))
            this.wCount += 1;
        if (WDL.equals("무"))
            this.dCount += 1;
        if (WDL.equals("패"))
            this.lCount += 1;

        this.scoreVoList.add(scoreVo);
    }
}
