package co.kr.fifamatch.model.vo;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class ScoreVo {
    private Integer homeScore;
    private Integer awayScore;

    private Instant matchDt;

    public ScoreVo(String homeScore, String awayScore, String matchDt) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        this.homeScore = Integer.parseInt(homeScore);
        this.awayScore = Integer.parseInt(awayScore);
        this.matchDt = Instant.from(formatter.parse(matchDt));
    }
}
