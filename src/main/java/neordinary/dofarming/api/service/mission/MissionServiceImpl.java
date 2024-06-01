package neordinary.dofarming.api.service.mission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.repository.UserCategoryJpaRepository;
import neordinary.dofarming.domain.mapping.user_mission.UserMission;
import neordinary.dofarming.domain.mapping.user_mission.UserMissionJpaRepository;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.mission.repository.MissionJpaRepository;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.repository.UserJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final UserJpaRepository userJpaRepository;
    private final UserCategoryJpaRepository userCategoryJpaRepository;
    private final MissionJpaRepository missionJpaRepository;
    private final UserMissionJpaRepository userMissionJpaRepository;

    @Override
    public Mission recommendMission(User user) {
        User currentUser = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        List<UserCategory> userCategories = userCategoryJpaRepository.findByUser(currentUser);

        if (userCategories.isEmpty()) {
            throw new IllegalArgumentException("카테고리를 찾을 수 없습니다.");
        }

        // UserCategory 리스트 중 하나를 랜덤 선택
        Random random = new Random();
        int randomCategoryIndex = random.nextInt(userCategories.size());
        Category category = userCategories.get(randomCategoryIndex).getCategory();

        List<Mission> missions = missionJpaRepository.findByCategory(category);

        if (missions.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리에 대한 미션이 없습니다.");
        }

        // 미션 리스트 중 하나를 랜덤 선택
        int randomMissionIndex = random.nextInt(missions.size());
        Mission recommendedMission = missions.get(randomMissionIndex);

        // 추천된 미션을 UserMission에 저장
        UserMission userMission = UserMission.builder()
                .user(currentUser)
                .mission(recommendedMission)
                .build();

        userMissionJpaRepository.save(userMission);

        return recommendedMission;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void recommendMissionForAllUsers() {
        List<User> allUsers = userJpaRepository.findAll();

        for (User user : allUsers) {
            try {
                Mission recommendedMission = recommendMission(user);
                log.info("Recommended mission for user {}: {}", user.getId(), recommendedMission.getContent());
            } catch (Exception e) {
                log.error("Failed to recommend mission for user {}: {}", user.getId(), e.getMessage());
            }
        }
    }
}
