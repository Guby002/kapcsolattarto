package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.AdminDTO;
import hu.futureofmedia.task.contactsapi.entities.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin adminDTOToAdmin(AdminDTO adminDTO);
    AdminDTO adminToAdminDTO(Admin admin);

}
