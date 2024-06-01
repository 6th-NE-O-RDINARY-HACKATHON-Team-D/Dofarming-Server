package neordinary.dofarming.api.service.mission;

import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.user.User;

public interface MissionService {
    Mission recommendMission(User user);
}
