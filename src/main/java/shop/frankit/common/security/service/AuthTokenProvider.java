package shop.frankit.common.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import shop.frankit.common.properties.AppProperties;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.common.security.dto.UserPrincipal;
import shop.frankit.domain.user.entity.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthTokenProvider {
    private static final String AUTHORITIES_KEY = "roles";
    private static final String NAME_KEY = "name";

    private final AppProperties appProperties;
    private SecretKey secretKey;

    public AuthTokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, List.of(RoleType.USER.name()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()))
                .compact();
    }

    public String createToken(String email, Collection<RoleType> roles) {
        Date now = new Date();
        List<String> roleNames = roles.stream()
                .map(RoleType::name)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, roleNames)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getTokenClaims(token);

        // 역할(role) 변환
        List<String> roleStrings = claims.get(AUTHORITIES_KEY, List.class);
        Set<RoleType> roles = roleStrings.stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toSet());

        // Spring Security의 GrantedAuthority 생성
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        UserPrincipal principal = new UserPrincipal(new User(
                claims.getSubject(),
                roles
        ));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Claims getTokenClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
