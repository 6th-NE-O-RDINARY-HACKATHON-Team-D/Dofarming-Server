package neordinary.dofarming.api.service.auth;


import neordinary.dofarming.api.controller.auth.dto.response.PostSocialRes;
import neordinary.dofarming.domain.user.enums.SocialType;

public interface AuthService {

    PostSocialRes socialLogin(SocialType socialType, String authorizationCode);


}
