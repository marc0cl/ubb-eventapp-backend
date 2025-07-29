package com.ubb.eventapp.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.eventapp.dto.AuthenticationRequest;
import com.ubb.eventapp.dto.AuthenticationResponse;
import com.ubb.eventapp.dto.RegisterRequest;
import com.ubb.eventapp.model.Token;
import com.ubb.eventapp.model.TokenType;
import com.ubb.eventapp.model.User;
import com.ubb.eventapp.model.UserState;
import com.ubb.eventapp.model.Role;
import com.ubb.eventapp.model.RoleName;
import com.ubb.eventapp.repository.RoleRepository;
import com.ubb.eventapp.repository.TokenRepository;
import com.ubb.eventapp.repository.UserRepository;
import com.ubb.eventapp.security.AuthConstants;
import com.ubb.eventapp.security.services.JwtService;
import com.ubb.eventapp.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        RoleName roleName;
        String email = request.getEmail();
        boolean external = request.isExternal();
        if (email.endsWith("@alumnos.ubiobio.cl")) {
            roleName = RoleName.ALUMNO;
        } else if (email.endsWith("@ubiobio.cl")) {
            roleName = external ? RoleName.EXTERNO : RoleName.DOCENTE;
        } else {
            roleName = RoleName.ALUMNO;
        }

        Role role = roleRepository.findByNombre(roleName)
                .orElseThrow();

        User user = User.builder()
                .correoUbb(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .nombres(request.getFirstName())
                .apellidos(request.getLastName())
                .estado(UserState.ACTIVO)
                .roles(java.util.Set.of(role))
                .build();
        User savedUser = userRepository.save(user);
        UserDetails userDetails = asUserDetails(savedUser);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateToken(userDetails);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw e;
        }
        User user = userRepository.findByCorreoUbb(request.getEmail())
                .orElseThrow();
        UserDetails userDetails = asUserDetails(user);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateToken(userDetails);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(AuthConstants.BEARER_PREFIX)) {
            return;
        }
        refreshToken = authHeader.substring(AuthConstants.BEARER_PREFIX.length());
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByCorreoUbb(userEmail).orElseThrow();
            UserDetails userDetails = asUserDetails(user);
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                String accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                response.setContentType(AuthConstants.CONTENT_TYPE_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private UserDetails asUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getCorreoUbb())
                .password(user.getPassword() == null ? "" : user.getPassword())
                .authorities(user.getRoles() == null ? java.util.List.of() :
                        user.getRoles().stream()
                                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre().name()))
                                .toList())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
