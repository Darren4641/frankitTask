package shop.frankit.common.response;


import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("D-0", "OK"),
    ERROR("D-99", "ERROR"),
    INVALID_PARAMETER("D-01", "입력값이 올바르지 않습니다."),
    ALREADY_SIGNUP("D-02", "이미 회원가입된 계정입니다."),
    USER_NOT_FOUND("D-03", "가입된 계정이 없습니다."),
    ILLEGAL_TOKEN_ERROR("D-995", "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN_ERROR("D-996", "토큰이 만료되었습니다."),
    EXPIRED_TOKEN_ERROR("D-997", "토큰이 만료되었습니다."),
    INVALID_TOKEN_ERROR("D-998", "토큰이 올바르지 않습니다."),
    SECURITY_ERROR("D-999", "인증에 실패하였습니다.");

    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
