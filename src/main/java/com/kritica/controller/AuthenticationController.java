package com.kritica.controller;


import com.kritica.security.jwt.AuthService;
import com.kritica.security.jwt.JwtUtils;
import com.kritica.security.request.LoginRequest;
import com.kritica.security.request.SignupRequest;
import com.kritica.security.response.UserInfoResponse;
import com.kritica.security.service.UserDetailsImpl;
import com.kritica.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final AuthenticationService authenticationService;

    //POST Signin -> LoginRequest -> UserInfoResponse
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){

        try {
            Authentication authentication = authService.authenticate(loginRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateJwtToken(userDetails);
            List<String> roles= userDetails.getAuthorities().stream()
                    .map(item->item.getAuthority()).toList();

            UserInfoResponse response = new UserInfoResponse();
            response.setToken(token);
            response.setRoles(roles);
            response.setUsername(userDetails.getUsername());
            response.setId(userDetails.getId());
            return ResponseEntity.ok(response);

        } catch (AuthenticationServiceException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message","Bad credentials");
            map.put("status",false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

    }

    //Post Signup  ->SignupRequest  -> MessageResponse
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        return authenticationService.registerUser(signupRequest);
    }

    //Post signout -> ->MessageResponse

    //Get the Current Username of the authenticated user to show
    //  the username on ui -> -> String Username

    //Get User Info to show user profile  ->  -> UserInfoResponse

    //Get ALl sellers ->  -> pageNumber ->UserResponse
}
