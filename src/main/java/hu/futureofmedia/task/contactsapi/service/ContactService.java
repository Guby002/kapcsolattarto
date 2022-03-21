package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;

import java.util.List;

public interface ContactService {

    void save(Contact contact);
    void delete(Long id);
    List<ContactForListDTO> findTenForUser(int pageNo);
    ContactDTO findById(Long id);
}
