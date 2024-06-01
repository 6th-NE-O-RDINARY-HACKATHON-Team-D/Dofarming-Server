package neordinary.dofarming.api.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.auth.dto.response.GetKakaoRes;
import neordinary.dofarming.api.controller.auth.dto.response.PostSocialRes;
import neordinary.dofarming.api.service.auth.social.kakao.KakaoLoginService;
import neordinary.dofarming.common.exceptions.BaseException;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.enums.SocialType;
import neordinary.dofarming.domain.user.repository.UserJpaRepository;
import neordinary.dofarming.utils.RedisProvider;
import neordinary.dofarming.utils.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static neordinary.dofarming.common.BaseEntity.State.ACTIVE;
import static neordinary.dofarming.common.code.status.ErrorStatus.INVALID_OAUTH_TYPE;
import static neordinary.dofarming.common.code.status.ErrorStatus.NOT_FIND_USER;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisProvider redisProvider;
    private final KakaoLoginService kakaoLoginService;



    @Override
    @Transactional
    public PostSocialRes socialLogin(SocialType socialType, String authorizationCode) {
        switch (socialType){
            case KAKAO: {
                GetKakaoRes getKakaoRes = kakaoLoginService.getUserInfo(kakaoLoginService.getAccessToken(authorizationCode));

                boolean isRegistered = userJpaRepository.existsByUsernameAndSocialTypeAndState(getKakaoRes.getId(), SocialType.KAKAO, ACTIVE);

                if (!isRegistered) {
                    User user = AuthConverter.toUser(getKakaoRes);
                    user.setIsFinished(false);
                    user.setIsEvaluated(false);
                    userJpaRepository.save(user);
                }
                User user = userJpaRepository.findByUsernameAndState(getKakaoRes.getId(), ACTIVE)
                        .orElseThrow(() -> new BaseException(NOT_FIND_USER));
                String accessToken = jwtProvider.generateToken(user);
                String refreshToken = jwtProvider.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, refreshToken);
                return AuthConverter.toPostSocialRes(user, accessToken, refreshToken);
            }
            case GOOGLE: {
                //TODO 구글 로그인
            }
            case NAVER: {
                //TODO 네이버 로그인
            }
            default:{
                throw new BaseException(INVALID_OAUTH_TYPE);
            }

        }
    }




    private void saveUserToken(User user, String refreshToken) {
        redisProvider.setValueOps(user.getUsername(), refreshToken);
        redisProvider.expireValues(user.getUsername());
    }

    private void revokeAllUserTokens(User user) {
        redisProvider.deleteValueOps(user.getUsername());
    }


}

