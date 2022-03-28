package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {


    void delete(Long id);
    List<ContactForListDTO> findTenForUser(int pageNo);
    ContactDTO findById(Long id) throws RecordNotFoundException;

    void save(ContactDTO contactDTO);


    Long update(Long id, ContactDTO contactDTO);
}
