package neordinary.dofarming.api.service.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import neordinary.dofarming.api.controller.auth.dto.response.GetKakaoRes;
import neordinary.dofarming.api.controller.auth.dto.response.PostSocialRes;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.enums.SocialType;

import static neordinary.dofarming.domain.user.enums.Role.USER;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConverter {

    public static User toUser(GetKakaoRes getKakaoRes){
        return User.builder()
                .username(getKakaoRes.getId())
                .socialType(SocialType.KAKAO)
                .role(USER)
                .build();
    }
    public static PostSocialRes toPostSocialRes(User user, String accessToken, String refreshToken){
        return PostSocialRes.builder()
                .id(user.getId())
                .isFinished(false)
                .isEvaluated(false)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }



}
