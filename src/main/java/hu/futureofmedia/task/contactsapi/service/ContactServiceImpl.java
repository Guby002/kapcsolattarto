package hu.futureofmedia.task.contactsapi.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;

import org.springframework.stereotype.Service;

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
        try {
            this.checkValidPhoneNumber(contactDTO.getPhoneNumber());
        } catch (NumberParseException e) {
            e.printStackTrace();
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

    @Override
    public boolean checkValidPhoneNumber(String phoneNumber) throws NumberParseException {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
      try {
       Phonenumber.PhoneNumber checkPhoneNumber =  phoneUtil.parse(phoneNumber,"HU");
            return phoneUtil.isValidNumber(checkPhoneNumber);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return false;
      //TODO
    }
}
