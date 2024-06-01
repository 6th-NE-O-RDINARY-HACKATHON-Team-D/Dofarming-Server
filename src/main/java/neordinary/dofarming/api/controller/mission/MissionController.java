package neordinary.dofarming.api.controller.mission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.mission.dto.GetMissionByDateRes;
import neordinary.dofarming.api.controller.mission.dto.GetMissionCalendarRes;
import neordinary.dofarming.api.controller.mission.dto.RecommendMissionRes;
import neordinary.dofarming.api.controller.mission.dto.UploadMissionImageRes;
import neordinary.dofarming.api.service.mission.MissionService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.user.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static neordinary.dofarming.common.code.status.SuccessStatus.*;

@Slf4j
@Tag(name = "mission controller", description = "미션 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    @Operation(summary = "미션 추천 API", description = "미션을 추천합니다.")
    public BaseResponse<RecommendMissionRes> recommendMission(@AuthenticationPrincipal User user) {
        RecommendMissionRes recommendMissionRes = missionService.recommendMission(user);
            return BaseResponse.of(MIS_RECOMMEND_OK, recommendMissionRes);
    }

    @PostMapping(value = "/{missionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "미션 인증 API", description = "미션 인증 이미지를 업로드합니다.")
    public BaseResponse<UploadMissionImageRes> uploadMissionImage(@AuthenticationPrincipal User user, @PathVariable Long missionId, @RequestParam("file") MultipartFile file) {
        UploadMissionImageRes uploadMissionImageRes = missionService.uploadMissionImage(user, missionId, file);
        return BaseResponse.of(MIS_SUCCESS_OK, uploadMissionImageRes);
    }

    @GetMapping
    @Operation(summary = "날짜별 미션 조회 API", description = "날짜별 미션을 조회합니다. 날짜는 yyyy-mm-dd 형식으로 넘겨주세요.")
    public BaseResponse<GetMissionByDateRes> getMissionByDate(@AuthenticationPrincipal User user, @RequestParam String date) {
        GetMissionByDateRes getMissionByDateRes = missionService.getMissionByDate(user, date);
        return BaseResponse.of(MIS_GET_OK, getMissionByDateRes);
    }

    @GetMapping("/calender")
    @Operation(summary = "미션 홈 화면 조회 API", description = "미션 홈 화면(캘린더, 오늘 미션)을 조회합니다.")
    public BaseResponse<GetMissionCalendarRes> getMissionByDate(@AuthenticationPrincipal User user) {
        GetMissionCalendarRes getMissionCalendarRes = missionService.getMissionCalendar(user);
        return BaseResponse.of(MIS_MISSION_GET_OK, getMissionCalendarRes);
    }

}
