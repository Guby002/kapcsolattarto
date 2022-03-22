package hu.futureofmedia.task.contactsapi.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PreUpdate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService  {
    private ContactMapper contactMapper;
    private ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public void save(ContactDTO contactDTO){
        contactDTO.setStat(Boolean.TRUE);
        contactRepository.save(contactMapper.toContact(contactDTO));

    }
    @Override
    public boolean validatePhoneNumber(String phoneNumber) throws NumberParseException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, "HU");
        return phoneNumberUtil.isValidNumber(numberProto);
    }

    @Override
    @PreUpdate
    public ResponseEntity update(Long id , ContactDTO contactDTO) {
        if(contactRepository.findById(id).isPresent()) {
            contactDTO.setStat(Boolean.TRUE);
            contactDTO.setId(id);
            contactRepository.delete(contactMapper.toContact(contactDTO));
            contactRepository.save(contactMapper.toContact(contactDTO));
            return ResponseEntity.status(HttpStatus.OK).body("Updated");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't update contactor");
        }
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }


    @Override
    public List<ContactForListDTO> findTenForUser(int pageNo) {
        List<Contact> contacts = contactRepository.findAll();

        return contactMapper.toContactForListListDto(contacts)
                        .stream()
                        .skip((pageNo-1) * 10)
                        .limit(10)
                        .filter(e->e.getStat().equals(Boolean.TRUE))
                        .collect(Collectors.toList());
    }
    @Override
    public ContactDTO findById (Long id){
        return contactMapper.toContactDto(contactRepository.findById(id));
    }
}
