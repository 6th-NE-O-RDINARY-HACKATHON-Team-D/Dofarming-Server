package neordinary.dofarming.api.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.user.dto.response.GetPointRes;
import neordinary.dofarming.api.controller.user.dto.response.GetUserRes;
import neordinary.dofarming.api.controller.user.dto.response.PatchUserRes;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.repository.UserCategoryJpaRepository;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.repository.UserJpaRepository;
import neordinary.dofarming.utils.s3.S3Provider;
import neordinary.dofarming.utils.s3.dto.S3UploadRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserCategoryJpaRepository userCategoryJpaRepository;
    private final S3Provider s3Provider;

    @Override
    @Transactional
    public PatchUserRes additionalInfo(User user, MultipartFile profile, String nickname) {
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

    @Override
    public GetUserRes getMyPage(User user) {
        List<UserCategory> userCategories = userCategoryJpaRepository.findByUser(user);
        List<GetPointRes> getPointRes = userCategories.stream()
                .map(userCategory -> GetPointRes.builder()
                        .categoryId(userCategory.getCategory().getId())
                        .wholePoint(userCategory.getWhole_point())
                        .isActive(userCategory.getIsActive())
                        .build(
                        ))
                .toList();
        return GetUserRes.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .pointList(getPointRes)
                .lastWeekMissionSuccessRate(0)
                .thisWeekMissionSuccessRate(0)
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
}
