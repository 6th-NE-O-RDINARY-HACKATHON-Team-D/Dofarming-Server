package neordinary.dofarming.api.controller.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetUserRes {

    private String nickname;
    private String profileImageUrl;
    private List<GetPointRes> pointList;
    private Double lastWeekMissionSuccessRate;
    private Double thisWeekMissionSuccessRate;

}
