package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegistrateUserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.entities.JwtResponse;
import hu.futureofmedia.task.contactsapi.entities.Role;
import hu.futureofmedia.task.contactsapi.entities.RoleName;
import hu.futureofmedia.task.contactsapi.entities.User;
import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.repositories.RoleRepository;
import hu.futureofmedia.task.contactsapi.repositories.UserRepository;
import hu.futureofmedia.task.contactsapi.security.AuthEntryPointJwt;
import hu.futureofmedia.task.contactsapi.security.Encoder;
import hu.futureofmedia.task.contactsapi.security.JwtTokenUtil;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Encoder encoder;
    private final UserMapper userMapper;

    @Transactional
    public ResponseEntity<?> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Transactional
    public ResponseEntity<?> registration(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        // Create new user's account
        RegistrateUserDTO  registrateUserDTO = new RegistrateUserDTO(userDTO.getUsername(),
                encoder.passwordEncoder().encode(userDTO.getPassword()),
                userDTO.getEmail());
        logger.info("Password: {}", registrateUserDTO.getPassword());
        Set<String> strRoles = (userDTO.getRole().stream()
                .map(s -> s.getName().toString())
                .collect(Collectors.toSet()));
        logger.info("ROLES {}", strRoles);
        Set<Role> roles = new HashSet<>();
        if (strRoles.isEmpty()) {
            Role userRole = roleRepository.findRoledByName(RoleName.USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findRoledByName(RoleName.ADMIN);
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findRoledByName(RoleName.USER);
                        roles.add(userRole);
                }
            });
        }
        User user = userMapper.registrateUserDTOToUser(registrateUserDTO, roles);
        userRepository.save(user);
        logger.info("ROLES {}", user.getRoles().size());
        return ResponseEntity.ok("User registered successfully!");
    }
}
