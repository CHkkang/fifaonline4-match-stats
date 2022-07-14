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
    Integer wCount;
    Integer lCount;
    Integer dCount;

    List<ScoreVo> scoreVoList;

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
