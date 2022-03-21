package hu.futureofmedia.task.contactsapi.service;

import com.google.i18n.phonenumbers.NumberParseException;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;

import java.util.List;

public interface ContactService {

    void save(ContactDTO contactDTO);
    void delete(Long id);
    List<ContactForListDTO> findTenForUser(int pageNo);
    ContactDTO findById(Long id);
    boolean checkValidPhoneNumber(String phoneNumber) throws NumberParseException;
}
