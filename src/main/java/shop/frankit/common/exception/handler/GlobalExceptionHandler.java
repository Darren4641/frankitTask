package shop.frankit.common.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.exception.dto.ExceptionMsg;
import shop.frankit.common.exception.dto.FieldErrorDetail;
import shop.frankit.common.response.ResultCode;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final ResponseEntity<ExceptionMsg> GENERIC_ERROR = new ResponseEntity<>(
            new ExceptionMsg(ResultCode.ERROR.getMessage(), ResultCode.ERROR.getCode(), false, List.of()),
            HttpStatus.INTERNAL_SERVER_ERROR
    );

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ExceptionMsg> handleApiErrorException(ApiErrorException ex) {
        return new ResponseEntity<>(
                new ExceptionMsg(ex.getResultCode().getMessage(), ex.getResultCode().getCode(), false, List.of()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMsg> handleMethodValidException(MethodArgumentNotValidException ex) {
        List<FieldErrorDetail> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> new FieldErrorDetail(((FieldError) error).getField(), error.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(
                new ExceptionMsg(ResultCode.INVALID_PARAMETER.getMessage(), ResultCode.INVALID_PARAMETER.getCode(), false, errors),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionMsg> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getCause();
        List<FieldErrorDetail> errors = switch (rootCause) {
            case InvalidFormatException formatException -> formatException.getPath().stream()
                    .map(reference -> new FieldErrorDetail(reference.getFieldName(), "[" + reference.getFieldName() + "] 가 타입이 올바르지 않습니다."))
                    .toList();
            case JsonMappingException mappingException -> mappingException.getPath().stream()
                    .map(reference -> new FieldErrorDetail(reference.getFieldName(), "[" + reference.getFieldName() + "] 가 누락되었습니다."))
                    .toList();
            default -> List.of(new FieldErrorDetail("", rootCause != null ? rootCause.getMessage() : "에러 발생"));
        };

        return new ResponseEntity<>(
                new ExceptionMsg(ResultCode.INVALID_PARAMETER.getMessage(), ResultCode.INVALID_PARAMETER.getCode(), false, errors),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionMsg> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(
                new ExceptionMsg(
                        ResultCode.INVALID_PARAMETER.getMessage(),
                        ResultCode.INVALID_PARAMETER.getCode(),
                        false,
                        List.of(new FieldErrorDetail(ex.getParameterName(), ex.getMessage()))
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMsg> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = ex.getRequiredType() != null && ex.getRequiredType().isEnum()
                ? "유효한 값: " + List.of(ex.getRequiredType().getEnumConstants()).toString()
                : "Invalid value for parameter '" + ex.getName() + "'";

        return new ResponseEntity<>(
                new ExceptionMsg(
                        ResultCode.INVALID_PARAMETER.getMessage(),
                        ResultCode.INVALID_PARAMETER.getCode(),
                        false,
                        List.of(new FieldErrorDetail(ex.getName(), message))
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMsg> handleException(Exception ex) {
        ex.printStackTrace();
        return GENERIC_ERROR;
    }
}
