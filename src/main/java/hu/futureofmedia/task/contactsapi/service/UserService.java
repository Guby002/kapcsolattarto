package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.entities.JwtResponse;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectEmailException;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectUserNameException;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public interface UserService {
    String userRegistration(RegisterUserDTO newUserDTO) throws IncorrectUserNameException, IncorrectEmailException;
    JwtResponse login(LoginDTO loginDTO);
    void delete(String username) throws SQLException;
   // String changeRoleForUser(String username) throws SQLException;;
}
