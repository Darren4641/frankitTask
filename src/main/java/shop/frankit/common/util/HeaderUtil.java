package shop.frankit.common.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public final class HeaderUtil {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private HeaderUtil() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static Optional<String> getAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HEADER_AUTHORIZATION))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .map(header -> header.substring(TOKEN_PREFIX.length()));
    }
}