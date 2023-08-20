package panomete.project.cometopimabe.security.controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import panomete.project.cometopimabe.security.entity.Users;
import panomete.project.cometopimabe.security.mapper.AutoAuthMapper;
import panomete.project.cometopimabe.security.payload.request.LoginRequest;
import panomete.project.cometopimabe.security.payload.request.RegisterRequest;
import panomete.project.cometopimabe.security.payload.request.UpdateRequest;
import panomete.project.cometopimabe.security.payload.response.AuthDto;
import panomete.project.cometopimabe.security.payload.response.JwtResponse;
import panomete.project.cometopimabe.security.service.AuthService;
import panomete.project.cometopimabe.security.utility.JwtTokenUtil;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication API", description = "the authentication API")
public class AuthController {
    final AuthService authService;
    final AuthenticationManager authenticationManager;
    final JwtTokenUtil jwtTokenUtil;
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    @Operation(summary = "Login", description = "Login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest login) {
        if (login.username() == null) {
            return createToken(loginWithEmail(login.email(), login.password()));
        }
        if (login.email() == null) {
            return createToken(loginWithUsername(login.username(), login.password()));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or email is required");
    }

    private ResponseEntity<JwtResponse> createToken(Users user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password is incorrect");
        }
        if(!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this account is locked");
        }
        JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateJWT(user, 604800L));
        return ResponseEntity.ok(jwtResponse);
    }

    private Users loginWithUsername(String username, String password) {
        Users user = authService.getUserByUsername(username);
        return loginProcess(username, password, user);
    }

    private Users loginWithEmail(String email, String password) {
        Users user = authService.getUserByEmail(email);
        return loginProcess(email, password, user);
    }

    private Users loginProcess(String username, String password, Users user) {
        if (!checkAuth(user, password)) {
            return null;
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("user {} successfully logged in", username);
        } catch (Exception e) {
            log.error("login process error: {}", e.getMessage());
        }
        return user;
    }

    private boolean checkAuth(Users user, String password) {
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @PostMapping("/signup")
    @Operation(summary = "Create a new user", description = "Create a new user")
    public ResponseEntity<AuthDto> createUserAccount(@RequestBody RegisterRequest user) {
        Users newAccount = authService.createUser(user);
        return ResponseEntity.ok(
                AutoAuthMapper.MAPPER.toAuthDto(newAccount)
        );
    }

    @GetMapping("/details")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user details", description = "Get user details")
    public ResponseEntity<AuthDto> getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = authService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(
                AutoAuthMapper.MAPPER.toAuthDto(user)
        );
    }

    @GetMapping("/refresh")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Refresh token", description = "Refresh token")
    public ResponseEntity<JwtResponse> refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String refreshedToken = jwtTokenUtil.refreshJWT(token, 604800L);
        return ResponseEntity.ok(
                new JwtResponse(refreshedToken)
        );
    }

    @GetMapping("/credentials")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user credentials", description = "Get user credentials")
    public ResponseEntity<Claims> getUserCredentials(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(
                jwtTokenUtil.getClaimsFromToken(token)
        );
    }

    @PutMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Update user details", description = "Update user details")
    public ResponseEntity<AuthDto> updateUserDetails(@RequestBody UpdateRequest update) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = authService.getUserByUsername(auth.getName()).getId();
        Users updated = authService.updateUser(userId, update);
        return ResponseEntity.ok(
                AutoAuthMapper.MAPPER.toAuthDto(updated)
        );
    }
}
