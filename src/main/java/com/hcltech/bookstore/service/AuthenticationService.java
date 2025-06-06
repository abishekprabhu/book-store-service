package com.hcltech.bookstore.service;

import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.mapper.User.UserMapper;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.User;
import com.hcltech.bookstore.repository.UserRepository;
import com.hcltech.bookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

/*    public AuthenticationService(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }*/

    public AuthenticationRequestDto register(final AuthenticationRequestDto authenticationRequestDto) {
        User user;
        log.info("AUTHENTICATION REQUEST DTO : {}", authenticationRequestDto);
        if ("AUTHOR".equalsIgnoreCase(authenticationRequestDto.getRoles())) {
            user = toAuthor(authenticationRequestDto);

        }else {
            user = toCustomer(authenticationRequestDto);

        }
//        final Author user = toAuthor(authenticationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final User result = userRepository.save(user);
        log.info("USER : {}", result);
        return userMapper.toAuthenticationRequestDto(result);
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(),
                                                        authenticationRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            final UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(
                    authenticationRequestDto.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            return new AuthenticationResponseDto(jwt);
        }
        throw new UsernameNotFoundException(authenticationRequestDto.getUsername() + " not found");
    }

    private Customer toCustomer(AuthenticationRequestDto dto) {
        log.info("AUTH DTO :{}", dto);
        return userMapper.toCustomer(dto);
    }
    private Author toAuthor(AuthenticationRequestDto dto) {
        return userMapper.toAuthor(dto);
    }

/*    private AuthenticationRequestDto toAuthenticationRequestDto(User user) {
        final AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setUsername(user.getUsername());
         authenticationRequestDto.setPassword(user.getPassword());
        authenticationRequestDto.setRoles(user.getRoles());
        return authenticationRequestDto;
    }*/
}
