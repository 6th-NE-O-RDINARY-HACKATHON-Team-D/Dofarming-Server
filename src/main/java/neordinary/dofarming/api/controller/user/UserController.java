package neordinary.dofarming.api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.user.dto.response.GetUserRes;
import neordinary.dofarming.api.controller.user.dto.response.PatchUserRes;
import neordinary.dofarming.api.service.user.UserService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.user.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static neordinary.dofarming.common.code.status.SuccessStatus.*;

@Slf4j
@Tag(name = "user controller", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    //추가 정보 입력
    @Operation(summary = "추가 정보 입력 API",description = "추가 정보를 입력합니다.")
    @PatchMapping("/additional-info")
    public BaseResponse<PatchUserRes> additionalInfo(@AuthenticationPrincipal User user,
                                                     @RequestParam("nickname") String nickname,
                                                     @RequestParam(value = "profile", required = false) MultipartFile profile) {
        return BaseResponse.of(ADDITIONAL_INFO_OK, userService.additionalInfo(user, profile, nickname));
    }

    // 마이페이지 조회
    @Operation(summary = "마이페이지 조회 API",description = "마이페이지를 조회합니다.")
    @GetMapping
    public BaseResponse<GetUserRes> getMyPage(@AuthenticationPrincipal User user) {
        return BaseResponse.of(GET_MY_PAGE_OK, userService.getMyPage(user));
    }


    // 마이페이지 회원 정보 수정
    @Operation(summary = "프로필 수정 API",description = "닉네임과 프로필 사진을 수정합니다.")
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<PatchUserRes> patchProfile(@AuthenticationPrincipal User user,
                                            @RequestParam("nickname") String nickname,
                                            @RequestParam(value = "profile", required = false) MultipartFile profile) {
        return BaseResponse.of(PROFILES_UPDATE_OK, userService.patchProfile(user, profile, nickname));
    }
}
