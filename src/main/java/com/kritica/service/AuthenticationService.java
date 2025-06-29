package com.kritica.service;

import com.kritica.model.AppRole;
import com.kritica.model.Role;
import com.kritica.model.Users;
import com.kritica.repository.RoleRepository;
import com.kritica.repository.UsersRepository;
import com.kritica.security.request.SignupRequest;
import com.kritica.security.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(SignupRequest signupRequest){
        if(usersRepository.existsByUsername((signupRequest.getUsername()))){
            return ResponseEntity.badRequest().body(new MessageResponse(1,"Error: Username is already taken!"));
        }
        if(usersRepository.existsByEmailAddress((signupRequest.getEmail()))){
            return ResponseEntity.badRequest().body(new MessageResponse(1,"Error: Email is already taken!"));
        }

        Users user= new Users();
        user.setUsername(signupRequest.getUsername());
        user.setEmailAddress(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles= signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRoles==null){
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Roles is not found"));
            roles.add(userRole);
        }else{
            //admin -> Role_admin
            strRoles.forEach(role ->{
                switch (role){
                    case "admin":
                        Role adminRole= roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Role is not found"));
                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole= roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(()-> new RuntimeException("Role is not found"));
                        roles.add(sellerRole);
                        break;
                        default:
                            Role userRole= roleRepository.findByRoleName(AppRole.ROLE_USER)
                                    .orElseThrow(()-> new RuntimeException("Role is not found"));
                            roles.add(userRole);
                            break;

                }
                    });
            user.setRoles(roles);
            usersRepository.save(user);
            return ResponseEntity.ok(new MessageResponse(0, "User regiester successfully"));
        }


        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
