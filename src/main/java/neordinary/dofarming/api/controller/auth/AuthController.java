package neordinary.dofarming.api.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.auth.dto.response.PostSocialRes;
import neordinary.dofarming.api.service.auth.AuthService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.user.enums.SocialType;
import org.springframework.web.bind.annotation.*;

import static neordinary.dofarming.common.code.status.SuccessStatus.OAUTH_OK;

@Slf4j
@Tag(name = "auth controller", description = "인증 필요 없는 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/health")
    @Operation(summary = "서버 상태 확인 API",description = "서버 상태를 확인합니다.")
    public BaseResponse<String> health() {
        return BaseResponse.onSuccess("I'm healthy");
    }

    @PostMapping("/{socialType}/login")
    @Operation(summary = "소셜 로그인 API",description = "소셜 로그인을 진행합니다.")
    public BaseResponse<PostSocialRes> login(@PathVariable(name="socialType") String socialLoginPath,
                                             @RequestParam("code") String code) {
        SocialType socialType = SocialType.valueOf(socialLoginPath.toUpperCase());
        return BaseResponse.of(OAUTH_OK, authService.socialLogin(socialType, code));
    }


}
