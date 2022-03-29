package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toContactDto(Contact contact);
    Contact toContact (ContactDTO contactDTO);
    ContactForListDTO toContactForListDto (Contact contact);
    @AfterMapping
    default void afterToContactForListDto(Contact contact, @MappingTarget final ContactForListDTO contactForListDTO){
        contactForListDTO.setName(contact.getFirstName()+" "+contact.getSecondName());
        contactForListDTO.setCompanyName(contact.getCompany().getName());
    }
    void updateContactFromContactDTO(ContactDTO contactDTO, @MappingTarget Contact contact);

}