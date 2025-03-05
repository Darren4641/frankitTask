package shop.frankit.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import shop.frankit.common.response.ResultCode;

import java.io.IOException;
import java.util.Map;

@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 사용

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // JSON 응답 생성
        String jsonResponse = objectMapper.writeValueAsString(Map.of(
                "code", ResultCode.SECURITY_ERROR.getCode(),
                "message", ResultCode.SECURITY_ERROR.getMessage()
        ));

        response.getWriter().print(jsonResponse);
    }
}