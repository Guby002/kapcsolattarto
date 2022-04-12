package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegistrateUserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface UserService {
    ResponseEntity<?> registration(UserDTO userDTO);
    ResponseEntity<?> login( LoginDTO loginDTO);
}
