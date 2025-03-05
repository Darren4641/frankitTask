package shop.frankit.common.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.frankit.common.security.dto.UserPrincipal;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.repository.UserRepository;

@Slf4j
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by email: " + email);

        User user = userRepository.findByEmailDsl(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new UserPrincipal(user);
    }
}