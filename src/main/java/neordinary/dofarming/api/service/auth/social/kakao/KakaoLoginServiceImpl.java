package neordinary.dofarming.api.service.auth.social.kakao;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.auth.dto.response.GetKakaoRes;
import neordinary.dofarming.common.exceptions.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static neordinary.dofarming.common.code.status.ErrorStatus.KAKAO_TOKEN_RECEIVE_FAIL;
import static neordinary.dofarming.common.code.status.ErrorStatus.TOKEN_NOT_FOUND;


@Slf4j
@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {


    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String KAKAO_TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String KAKAO_USER_INFO_URL;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URL;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_API_KEY;

    @Override
    public String getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", KAKAO_API_KEY);
        map.add("redirect_uri", REDIRECT_URL);
        map.add("code", authorizationCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        Map<String, Object> response = null;
        try {
            // 카카오 서버 호출하여 값 받음
            response = restTemplate.postForObject(KAKAO_TOKEN_URL, requestEntity, HashMap.class);
            log.info("response is {}", response);
            if(response != null && response.containsKey("access_token")) {
                return response.get("access_token").toString();
            } else {
                // 토큰이 없는 경우 예외 처리
                throw new BaseException(TOKEN_NOT_FOUND);
            }
        } catch (RestClientException e) {
            // RestClientException 예외 처리
            log.error("RestClientException 발생: {}", e.getMessage());
            throw new BaseException(KAKAO_TOKEN_RECEIVE_FAIL);
        }
    }

    @Override
    public GetKakaoRes getUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        log.info("getUserInfo accessToken is {}", accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

        String response = restTemplate.postForObject(KAKAO_USER_INFO_URL, requestEntity, String.class);
        JsonObject rootObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject accountObject = rootObject.getAsJsonObject("kakao_account");

        log.info("response is {}", response);

        String nickname = "";
        String id = "";

        if (accountObject.has("profile") && !accountObject.get("profile").isJsonNull()) {
            JsonObject profileObject = accountObject.getAsJsonObject("profile");
            if (profileObject.has("nickname") && !profileObject.get("nickname").isJsonNull()) {
                nickname = profileObject.get("nickname").getAsString();
            }
        }

        if (rootObject.has("id") && !rootObject.get("id").isJsonNull()) {
            id = rootObject.get("id").getAsString(); // 직접 rootObject에서 id 값을 가져옴
        }
        log.info("nickname is {}", nickname);
        log.info("id is {}", id);
        return GetKakaoRes.builder()
                .name(nickname)
                .id(id)
                .build();
    }

}

