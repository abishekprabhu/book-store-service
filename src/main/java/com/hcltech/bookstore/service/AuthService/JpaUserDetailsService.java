package com.hcltech.bookstore.service.AuthService;

import com.hcltech.bookstore.model.User;
import com.hcltech.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("Attempting to load user by username: {}", username);
        final User user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new UsernameNotFoundException(username));

        return toUserDetails(user);
    }

    private UserDetails toUserDetails(User user) {

        final UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(
                                                    user.getUsername())
                                                        .password(user.getPassword())
                                                        .roles(user.getRoles().toArray(new String[0]))
                                                        .build();
        log.debug("Converted User entity to UserDetails: {}", userDetails.getUsername());
        return userDetails;
    }
}
