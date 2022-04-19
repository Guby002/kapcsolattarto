package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.entities.*;
import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.repositories.RoleRepository;
import hu.futureofmedia.task.contactsapi.repositories.UserRepository;
import hu.futureofmedia.task.contactsapi.security.Encoder;
import hu.futureofmedia.task.contactsapi.security.JwtTokenUtil;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Override
    @Transactional
    public ResponseEntity<JwtResponse> login(LoginDTO loginDTO) {
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

    @Override
    @Transactional
    public void delete(String username) throws SQLException {
        User user = userRepository.getById(userRepository.findByUsername(username).get().getId());
        userRepository.delete(user);
        logger.debug("User deleted");
    }
    @Override
    @Transactional
    public ResponseEntity<?> userRegistration(RegisterUserDTO newUserDTO) {
        if (userRepository.existsByUsername(newUserDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(newUserDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        // Create new user's account
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(RoleName.USER));
        UserDTO user = new UserDTO();
        user.setUsername(newUserDTO.getUsername());

        user.setPassword(encoder.passwordEncoder().encode(newUserDTO.getPassword()));
        user.setEmail(newUserDTO.getEmail());

        user.setRoles(roles);
        userRepository.save(userMapper.userDTOToUser(user));
        return ResponseEntity.ok("User registered successfully!");
    }

}
