package hu.futureofmedia.task.contactsapi.mapper;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toContactDto(Contact contact);
    Contact toContact (ContactDTO contactDTO);
    ContactForListDTO toContactForListDto (Contact contact);
    Contact updateContactFromContactDTO (ContactDTO contactDTO);
    @AfterMapping
    default void afterToContactForListDto(Contact contact, @MappingTarget final ContactForListDTO contactForListDTO){
        contactForListDTO.setName(contact.getFirstName()+" "+contact.getSecondName());
        contactForListDTO.setCompanyName(contact.getCompany().getName());
    }

}