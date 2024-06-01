package neordinary.dofarming.api.service.mission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.mission.dto.UploadMissionImageRes;
import neordinary.dofarming.common.exceptions.BaseException;
import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.repository.UserCategoryJpaRepository;
import neordinary.dofarming.domain.mapping.user_mission.UserMission;
import neordinary.dofarming.domain.mapping.user_mission.UserMissionJpaRepository;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.mission.repository.MissionJpaRepository;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.repository.UserJpaRepository;
import neordinary.dofarming.utils.s3.S3Provider;
import neordinary.dofarming.utils.s3.dto.S3UploadRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

import static neordinary.dofarming.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final UserJpaRepository userJpaRepository;
    private final UserCategoryJpaRepository userCategoryJpaRepository;
    private final MissionJpaRepository missionJpaRepository;
    private final UserMissionJpaRepository userMissionJpaRepository;
    private final S3Provider s3Provider;

    @Override
    public Mission recommendMission(User user) {
        User currentUser = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        List<UserCategory> userCategories = userCategoryJpaRepository.findByUser(currentUser);

        if (userCategories.isEmpty()) {
            throw new BaseException(CANNOT_FIND_CATEGORY);
        }
        // UserCategory 리스트 중 하나를 랜덤 선택
        Random random = new Random();
        int randomCategoryIndex = random.nextInt(userCategories.size());
        Category category = userCategories.get(randomCategoryIndex).getCategory();

        List<Mission> missions = missionJpaRepository.findByCategory(category);

        if (missions.isEmpty()) {
            throw new BaseException(CANNOT_FIND_MISSION);
        }
        int randomMissionIndex = random.nextInt(missions.size());
        Mission recommendedMission = missions.get(randomMissionIndex);

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

    @Override
    public UploadMissionImageRes uploadMissionImage(User user, Long missionId, MultipartFile file) {
        User currentUser = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        UserMission userMission = userMissionJpaRepository.findById(missionId)
                .orElseThrow(() -> new BaseException(CANNOT_FIND_MISSION));

        S3UploadRequest request = S3UploadRequest.builder()
                .userId(user.getId())
                .dirName("mission")
                .build();
        String s3Url = s3Provider.multipartFileUpload(file, request);

        userMission.updateImage(s3Url);

        UserMission updatedUserMission = userMissionJpaRepository.save(userMission);

        return UploadMissionImageRes.builder()
                .nickname(user.getNickname())
                .imageUrl(updatedUserMission.getImage())
                .updatedAt(updatedUserMission.getUpdatedAt())
                .build();
    }

}
