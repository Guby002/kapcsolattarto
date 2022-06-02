package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toContactDto(Contact contact);
    Contact toContact (ContactDTO contactDTO);
    @Mapping(target = "name" , expression = "java( contact.getFirstName() + contact.getSecondName())")
    ContactForListDTO toContactForListDto (Contact contact);
   @AfterMapping
    default void afterToContactForListDto(Contact contact, @MappingTarget final ContactForListDTO contactForListDTO){
        contactForListDTO.setName(contact.getFirstName()+" "+contact.getSecondName());
        contactForListDTO.setCompanyName(contact.getCompany().getName());
    }

    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "status")
    @Mapping(ignore = true, target = "lastModify")
    @Mapping(ignore = true, target = "comment")
    void updateContactFromContactDTO(ContactDTO contactDTO, @MappingTarget Contact contact);
}
