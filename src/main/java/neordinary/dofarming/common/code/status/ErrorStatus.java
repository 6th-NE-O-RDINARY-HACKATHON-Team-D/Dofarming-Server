package neordinary.dofarming.common.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import neordinary.dofarming.common.code.BaseErrorCode;
import neordinary.dofarming.common.code.ErrorReasonDTO;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    /**
     * 400 : Request, Response 오류
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4001", "로그인 인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4003", "금지된 요청입니다."),
    RESPONSE_ERROR(HttpStatus.NOT_FOUND, "COMMON4004", "값을 불러오는데 실패하였습니다."),


    USERS_EMPTY_EMAIL( HttpStatus.BAD_REQUEST, "USER4000", "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(HttpStatus.BAD_REQUEST,"USER4001" , "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "USER4002","중복된 아이디입니다."),
    FAILED_TO_LOGIN(HttpStatus.BAD_REQUEST, "USER4003", "존재하지 않는 아이디거나 비밀번호가 틀렸습니다."),
    NOT_FIND_USER(HttpStatus.NOT_FOUND, "USER4000", "일치하는 유저가 없습니다."),
    EXIST_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "USER4005", "이미 존재하는 전화번호입니다."),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "USER4006", "핸드폰 번호 양식에 맞지 않습니다. 예시: +82-10-0000-0000"),

    EMPTY_JWT(HttpStatus.UNAUTHORIZED, "JWT4000", "JWT를 입력해주세요"),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "JWT4001", "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(HttpStatus.FORBIDDEN, "JWT4002", "권한이 없는 유저의 접근입니다."),

    KAKAO_TOKEN_RECEIVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "TOKEN4000", "카카오 서버로부터 액세스 토큰을 받는데 실패했습니다."),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4001", "토큰이 존재하지 않습니다."),

    INVALID_OAUTH_TYPE(HttpStatus.BAD_REQUEST, "OAUTH4000", "알 수 없는 소셜 로그인 형식입니다."),

    INVALID_PAGE(HttpStatus.BAD_REQUEST, "PAGE4000", "페이지는 0 이상이어야 합니다."),
    INVALID_SIZE_10(HttpStatus.BAD_REQUEST, "PAGE4001", "사이즈는 10이어야 합니다."),
    FILE_CONVERT(HttpStatus.BAD_REQUEST, "FILE4001", "파일 변환 실패."),
    S3_UPLOAD(HttpStatus.BAD_REQUEST, "S3UPLOAD4001", "S3 파일 업로드 실패."),

    CANNOT_FIND_CATEGORY(HttpStatus.BAD_REQUEST, "CATEGORY4000", "카테고리를 찾을 수 없습니다."),


    /**
     * 500 :  Database, Server 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000", "서버 에러, 관리자에게 문의 바랍니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5001", "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5002", "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5003", "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5004", "비밀번호 복호화에 실패하였습니다"),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5005", "예상치 못한 에러가 발생했습니다.");




    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}