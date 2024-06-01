package neordinary.dofarming.api.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.user.dto.response.GetPointRes;
import neordinary.dofarming.api.controller.user.dto.response.GetUserRes;
import neordinary.dofarming.api.controller.user.dto.response.PatchUserRes;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.repository.UserCategoryJpaRepository;
import neordinary.dofarming.domain.mapping.user_mission.UserMission;
import neordinary.dofarming.domain.mapping.user_mission.UserMissionJpaRepository;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.repository.UserJpaRepository;
import neordinary.dofarming.utils.s3.S3Provider;
import neordinary.dofarming.utils.s3.dto.S3UploadRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserCategoryJpaRepository userCategoryJpaRepository;
    private final UserMissionJpaRepository userMissionJpaRepository;
    private final S3Provider s3Provider;

    @Override
    @Transactional
    public PatchUserRes additionalInfo(User user, MultipartFile profile, String nickname) {
        if(profile != null) {
            String profileImageUrl = s3Provider.multipartFileUpload(profile, S3UploadRequest.builder().userId(user.getId()).dirName("profile").build());
            user.setProfileImageUrl(profileImageUrl);
        }
        user.setNickname(nickname);
        user.setIsFinished(true);
        userJpaRepository.save(user);
        return PatchUserRes.builder().
                nickname(user.getNickname()).
                profileImageUrl(user.getProfileImageUrl()).
                isFisished(user.getIsFinished()).
                isEvaluated(user.getIsEvaluated()).
                build();
    }

    @Override
    public GetUserRes getMyPage(User user) {
        List<UserCategory> userCategories = userCategoryJpaRepository.findByUser(user);
        int totalWholePoints = userCategories.stream()
                .mapToInt(UserCategory::getWhole_point)
                .sum();
        List<GetPointRes> getPointRes = userCategories.stream()
                .map(userCategory -> {
                    double percentage = (totalWholePoints == 0) ? 0 : Math.round((double) userCategory.getWhole_point() / totalWholePoints * 1000) / 10.0;
                    return new GetPointRes(
                            userCategory.getCategory().getId(),
                            userCategory.getWhole_point(),
                            percentage,
                            userCategory.getIsActive()
                    );
                })
                .toList();
        // 이번 주의 시작과 끝 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59);

        // 지난 주의 시작과 끝 계산
        LocalDateTime startOfLastWeek = startOfThisWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfThisWeek.minusWeeks(1);

        // 이번 주와 지난 주의 미션 성공률 계산
        double thisWeekSuccessRate = calculateMissionSuccessRate(user, startOfThisWeek, endOfThisWeek);
        double lastWeekSuccessRate = calculateMissionSuccessRate(user, startOfLastWeek, endOfLastWeek);

        return GetUserRes.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .pointList(getPointRes)
                .lastWeekMissionSuccessRate(lastWeekSuccessRate)
                .thisWeekMissionSuccessRate(thisWeekSuccessRate)
                .build();
    }
    @Override
    @Transactional
    public PatchUserRes patchProfile(User user, MultipartFile profile, String nickname) {
        if(profile != null) {
            String profileImageUrl = s3Provider.multipartFileUpload(profile, S3UploadRequest.builder().userId(user.getId()).dirName("profile").build());
            user.setProfileImageUrl(profileImageUrl);
        }
        user.setNickname(nickname);
        userJpaRepository.save(user);
        return PatchUserRes.builder().
                nickname(user.getNickname()).
                profileImageUrl(user.getProfileImageUrl()).
                build();
    }


    private double calculateMissionSuccessRate(User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<UserMission> missions = userMissionJpaRepository.findByUserAndCreatedAtBetween(user, startDate, endDate);
        long totalMissions = missions.size();
        long successfulMissions = missions.stream().filter(UserMission::getIsSuccess).count();

        return (totalMissions == 0) ? 0 : (double) successfulMissions / totalMissions * 100;
    }
}
