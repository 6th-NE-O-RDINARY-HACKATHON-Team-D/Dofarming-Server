package neordinary.dofarming.api.controller.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RecommendMissionRes {

    private String nickname;
    private Long userMissionId;
    private Boolean isSuccess;
    private String missionContent;
    private LocalDateTime createdAt;
}
