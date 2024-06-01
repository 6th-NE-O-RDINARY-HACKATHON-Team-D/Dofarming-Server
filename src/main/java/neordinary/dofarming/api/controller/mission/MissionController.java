package neordinary.dofarming.api.controller.mission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.mission.dto.UploadMissionImageRes;
import neordinary.dofarming.api.service.mission.MissionService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.user.User;
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
    @Operation(summary = "미션 추천 API", description = "추천 미션을 조회합니다.")
    public BaseResponse<Mission> recommendMission(@AuthenticationPrincipal User user) {
            Mission recommendedMission = missionService.recommendMission(user);
            return BaseResponse.of(MIS_RECOMMEND_OK, recommendedMission);
    }

    @PostMapping("/{missionId}")
    @Operation(summary = "미션 인증 API", description = "미션 인증 이미지를 업로드합니다.")
    public BaseResponse<UploadMissionImageRes> uploadMissionImage(@AuthenticationPrincipal User user, @PathVariable Long missionId, @RequestParam("file") MultipartFile file) {
        UploadMissionImageRes uploadMissionImageRes = missionService.uploadMissionImage(user, missionId, file);
        return BaseResponse.of(MIS_SUCCESS_OK, uploadMissionImageRes);
    }


}
