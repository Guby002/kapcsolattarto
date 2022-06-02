package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Role;
import hu.futureofmedia.task.contactsapi.entities.User;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper{
    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
    //User registrateUserDTOToUser (RegistrateUserDTO registrateUserDTO, Role role);
    UserDTO userDetailsToUserDTO(UserDetails userDetails);
    UserForListDTO userToUserForListDTO(User user);
}
