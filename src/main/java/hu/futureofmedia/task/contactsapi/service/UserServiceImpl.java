package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserForListDTO;
import hu.futureofmedia.task.contactsapi.entities.*;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectEmailException;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectUserNameException;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
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
    public JwtResponse login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = jwtTokenUtil.generateJwtToken(authentication);
        logger.info(jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    @Transactional
    public void delete(String username) {
        User user = findUser(username);
        userRepository.delete(user);
        logger.debug("User deleted");
    }
    @Override
    @Transactional
    public List<UserForListDTO> loadUsers(){
        List<User> listData = userRepository.findAll();
        return listData.stream().map(userMapper::userToUserForListDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public String userRegistration(RegisterUserDTO newUserDTO) throws IncorrectUserNameException, IncorrectEmailException {
        if (userRepository.existsByUsername(newUserDTO.getUsername())) {
            throw new IncorrectUserNameException("User name taken ");
        }
        if (userRepository.existsByEmail(newUserDTO.getEmail())) {
            throw new IncorrectEmailException("Email taken");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(RoleName.USER));
        UserDTO user = new UserDTO();
        user.setUsername(newUserDTO.getUsername());

        user.setPassword(encoder.passwordEncoder().encode(newUserDTO.getPassword()));
        user.setEmail(newUserDTO.getEmail());

        user.setRoles(roles);
        userRepository.save(userMapper.userDTOToUser(user));
        return "User registered successfully!";
    }

    @Transactional
    public User findUser(String username){
        logger.debug("Search for one user by UserName");
        return userRepository.findUserByUsername(username).orElseThrow(RecordNotFoundException::new);
    }
}
