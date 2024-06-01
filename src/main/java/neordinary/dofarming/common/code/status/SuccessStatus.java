package neordinary.dofarming.common.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import neordinary.dofarming.common.code.BaseCode;
import neordinary.dofarming.common.code.ReasonDTO;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    OK(HttpStatus.OK, "COMMON2000", "성공입니다."),

    OAUTH_OK(HttpStatus.OK, "USER2001", "소셜 로그인 성공"),
    //마이페이지 조회 성공
    GET_MY_PAGE_OK(HttpStatus.OK, "USER2002", "마이페이지 조회 성공"),
    //프로필 수정 성공
    PROFILES_UPDATE_OK(HttpStatus.OK, "USER2004", "프로필 수정 성공"),

    // 카테고리 성공
    CATEGORY_UPDATE_OK(HttpStatus.OK, "CATEGORY2000", "카테고리 상태 변경 성공"),
    GET_CATEGORY_OK(HttpStatus.OK, "CATEGORY2001", "카테고리 리스트 조회 성공"),

    // 질문 점수 성공
    SAVE_POINTS_OK(HttpStatus.OK, "QUESTION2000", "질문 별 점수 부여 성공"),
    GET_POINTS_PERCENT_OK(HttpStatus.OK, "QUESTION2001", "카테고리 별 점수 비율 조회 성공"),

    //미션 추천 성공
    MIS_RECOMMEND_OK(HttpStatus.OK, "MISSION2001", "미션 추천 성공"),
    //미션 인증 사진 업로드 성공
    MIS_SUCCESS_OK(HttpStatus.OK, "MISSION2002", "미션 인증 성공"),
    //날짜별 미션 조회 성공
    MIS_GET_OK(HttpStatus.OK, "MISSION2003", "날짜별 미션 조회 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}