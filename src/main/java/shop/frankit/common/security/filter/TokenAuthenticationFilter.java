package shop.frankit.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.frankit.common.response.ResultCode;
import shop.frankit.common.security.service.AuthTokenProvider;
import shop.frankit.common.util.HeaderUtil;

import java.io.IOException;
import java.util.Map;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TokenAuthenticationFilter(AuthTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String tokenStr = HeaderUtil.getAccessToken(request).orElse(null);
            if (tokenStr != null) {
                Authentication authentication = tokenProvider.getAuthentication(tokenStr);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException ex) {
            handleException(response, ResultCode.INVALID_TOKEN_ERROR);
        } catch (ExpiredJwtException ex) {
            handleException(response, ResultCode.EXPIRED_TOKEN_ERROR);
        } catch (UnsupportedJwtException ex) {
            handleException(response, ResultCode.UNSUPPORTED_TOKEN_ERROR);
        } catch (IllegalArgumentException ex) {
            handleException(response, ResultCode.ILLEGAL_TOKEN_ERROR);
        }
    }

    private static void handleException(HttpServletResponse response, ResultCode resultCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // JSON 직렬화 (Jackson 사용)
        String jsonResponse = objectMapper.writeValueAsString(Map.of(
                "code", resultCode.getCode(),
                "message", resultCode.getMessage()
        ));

        response.getWriter().print(jsonResponse);
    }
}