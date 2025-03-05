package shop.frankit.common.exception;

import shop.frankit.common.response.ResultCode;

public class ApiErrorException extends RuntimeException {
    private final ResultCode resultCode;

    public ApiErrorException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}