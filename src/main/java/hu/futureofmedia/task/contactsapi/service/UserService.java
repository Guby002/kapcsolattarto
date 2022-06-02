package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserForListDTO;
import hu.futureofmedia.task.contactsapi.entities.JwtResponse;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectEmailException;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectUserNameException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    String userRegistration(RegisterUserDTO newUserDTO) throws IncorrectUserNameException, IncorrectEmailException;
    JwtResponse login(LoginDTO loginDTO);
    void delete(String username) throws SQLException;
    List<UserForListDTO> loadUsers();
}
