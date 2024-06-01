package neordinary.dofarming.api.service.mission;

import neordinary.dofarming.api.controller.mission.dto.GetMissionByDateRes;
import neordinary.dofarming.api.controller.mission.dto.GetMissionCalendarRes;
import neordinary.dofarming.api.controller.mission.dto.RecommendMissionRes;
import neordinary.dofarming.api.controller.mission.dto.UploadMissionImageRes;
import neordinary.dofarming.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface MissionService {
    RecommendMissionRes recommendMission(User user);

    UploadMissionImageRes uploadMissionImage(User user, Long missionId, MultipartFile file);

    GetMissionByDateRes getMissionByDate(User user, String date);

    GetMissionCalendarRes getMissionCalendar(User user);
}
