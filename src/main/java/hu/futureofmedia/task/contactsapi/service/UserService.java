package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.entities.JwtResponse;
import org.springframework.http.ResponseEntity;
public interface UserService {
    ResponseEntity<?> registration(UserDTO userDTO);
    ResponseEntity<JwtResponse> login(LoginDTO loginDTO);
}
