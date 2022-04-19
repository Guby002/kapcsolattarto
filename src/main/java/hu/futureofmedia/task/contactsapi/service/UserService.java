package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.entities.JwtResponse;
import org.springframework.http.ResponseEntity;
public interface UserService {
    ResponseEntity<?> userRegistration(RegisterUserDTO newUserDTO);
    ResponseEntity<JwtResponse> login(LoginDTO loginDTO);
}
