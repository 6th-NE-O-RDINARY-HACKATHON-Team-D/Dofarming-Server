package neordinary.dofarming.api.service.mission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.mission.dto.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
    @Transactional
    public RecommendMissionRes recommendMission(User user) {
        User currentUser = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        // 유저와 isActive가 true인 카테고리 조회
        List<UserCategory> userCategories = userCategoryJpaRepository.findByUserAndIsActive(currentUser, true);

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
                .isSuccess(false)
                .build();
        userMissionJpaRepository.save(userMission);

        return RecommendMissionRes.builder()
                .nickname(currentUser.getNickname())
                .userMissionId(userMission.getId())
                .isSuccess(userMission.getIsSuccess())
                .missionContent(userMission.getMission().getContent())
                .createdAt(userMission.getCreatedAt())
                .build();
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public void recommendMissionForAllUsers() {
        List<User> allUsers = userJpaRepository.findAll();

        for (User user : allUsers) {
            try {
                RecommendMissionRes recommendedMission = recommendMission(user);
                log.info("Recommended mission for user {}: {}", user.getId(), recommendedMission.getMissionContent());
            } catch (Exception e) {
                log.error("Failed to recommend mission for user {}: {}", user.getId(), e.getMessage());
            }
        }
    }

    @Override
    @Transactional
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

        userMission.updateImage(s3Url, true);

        UserMission updatedUserMission = userMissionJpaRepository.save(userMission);

        return UploadMissionImageRes.builder()
                .nickname(user.getNickname())
                .imageUrl(updatedUserMission.getImage())
                .updatedAt(updatedUserMission.getUpdatedAt())
                .build();
    }

    @Override
    public GetMissionByDateRes getMissionByDate(User user, String date) {
        User currentUser = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        // 문자열을 LocalDate로 변환
        LocalDateTime localDate = LocalDateTime.parse(date);

        // 해당 일의 시작과 끝을 구함
        LocalDateTime startOfDay = localDate.with(LocalTime.MIN);
        LocalDateTime endOfDay = localDate.with(LocalTime.MAX);

        List<UserMission> userMissions = userMissionJpaRepository.findByUserAndCreatedAtBetween(currentUser, startOfDay, endOfDay);

        if (!userMissions.isEmpty()) {
            UserMission userMission = userMissions.get(0);

            return GetMissionByDateRes.builder()
                    .userMissionId(userMission.getId())
                    .missionContent(userMission.getMission().getContent())
                    .isSuccess(userMission.getIsSuccess())
                    .image(userMission.getImage())
                    .updatedAt(String.valueOf(userMission.getUpdatedAt()))
                    .build();
        } else {
            return GetMissionByDateRes.builder().build();
        }
    }

    @Override
    public GetMissionCalendarRes getMissionCalendar(User user) {
        // 오늘 미션 조회
        LocalDate now = LocalDate.now();
        GetMissionByDateRes todayMission = getMissionByDate(user, now.toString());

        // 임의로 날짜 지정(2024-05-30)
//        LocalDate now = LocalDate.of(2024, Month.MAY, 30);

        // 한 달 캘린더 정보 조회
        LocalDateTime startDate = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);

        List<UserMission> userMissionsForMonth = userMissionJpaRepository.findByUserAndCreatedAtBetween(user, startDate, endDate);

        List<CalendarDto> calendarDtoList = userMissionsForMonth.stream().map(userMission -> {
            LocalDate missionDate = userMission.getCreatedAt().toLocalDate();
            return CalendarDto.builder()
                    .month(String.valueOf(missionDate.getMonthValue()))
                    .date(String.format("%02d-%02d", missionDate.getMonthValue(), missionDate.getDayOfMonth()))
                    .image(userMission.getImage())
                    .isSuccess(userMission.getIsSuccess())
                    .build();
        }).collect(Collectors.toList());

        return GetMissionCalendarRes.builder()
                .calendar(calendarDtoList)
                .todayMission(todayMission)
                .build();
    }

}
