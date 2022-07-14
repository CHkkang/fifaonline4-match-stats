package co.kr.fifamatch.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchDto {
    private String nickname1;
    private String nickname2;
    private String matchType;
    private Integer matchCount;
}
