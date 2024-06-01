package neordinary.dofarming.api.controller.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetMissionByDateRes {

    private String missionContent;
    private Boolean isSuccess;
    private String image;
    private String updatedAt;
}
