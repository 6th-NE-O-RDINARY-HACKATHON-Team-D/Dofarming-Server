package neordinary.dofarming.api.service.mission;

import neordinary.dofarming.api.controller.mission.dto.UploadMissionImageRes;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface MissionService {
    Mission recommendMission(User user);

    UploadMissionImageRes uploadMissionImage(User user, Long missionId, MultipartFile file);
}
