package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toContactDto(Contact contact);

    Contact toContact (ContactDTO contactDTO);

    List<ContactDTO> toContactDto(List<Contact> all);

    List<ContactForListDTO> toContactForListListDto(List<Contact> all);
    ContactForListDTO toContactForListDto (Contact contact);

    ContactDTO toContactDto(Optional<Contact> byId);
}