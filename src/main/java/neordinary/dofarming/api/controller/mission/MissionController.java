package neordinary.dofarming.api.controller.mission;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.service.mission.MissionService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "mission controller", description = "미션 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<BaseResponse<Mission>> recommendMission(@AuthenticationPrincipal User user) {
        try {
            Mission recommendedMission = missionService.recommendMission(user);
            return ResponseEntity.ok(BaseResponse.onSuccess(recommendedMission));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.onFailure("INTERNAL_SERVER_ERROR", "Failed to recommend mission", null));
        }
    }

}
