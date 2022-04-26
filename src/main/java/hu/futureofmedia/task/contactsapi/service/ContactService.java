package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ContactService {
    void delete(Long id) throws SQLException;
    List<ContactForListDTO> findTenForUser(int pageNo);
    ContactDTO findById(Long id) throws RecordNotFoundException;
    Long save(ContactDTO contactDTO);
    Long update(Long id, ContactDTO contactDTO) throws SQLException;
}
