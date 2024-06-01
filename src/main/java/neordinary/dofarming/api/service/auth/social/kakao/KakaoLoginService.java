package neordinary.dofarming.api.service.auth.social.kakao;


import neordinary.dofarming.api.controller.auth.dto.response.GetKakaoRes;

public interface KakaoLoginService {

    String getAccessToken(String authorizationCode);
    GetKakaoRes getUserInfo(String accessToken);
}
