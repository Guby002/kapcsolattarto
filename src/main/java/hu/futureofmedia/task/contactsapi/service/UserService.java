package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    List<ContactForListDTO> findTenForUser(int pageNo);
    ContactDTO findById(Long id) throws RecordNotFoundException;
    UserDTO getUserById(Long id) throws UsernameNotFoundException;
}
