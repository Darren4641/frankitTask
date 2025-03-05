package shop.frankit.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import shop.frankit.common.response.ResultCode;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // JSON 응답 생성
        String jsonResponse = objectMapper.writeValueAsString(Map.of(
                "code", ResultCode.SECURITY_ERROR.getCode(),
                "message", ResultCode.SECURITY_ERROR.getMessage()
        ));

        response.getWriter().print(jsonResponse);
    }
}