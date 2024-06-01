package neordinary.dofarming.api.controller.mission;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.service.mission.MissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "mission controller", description = "미션 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;
}
