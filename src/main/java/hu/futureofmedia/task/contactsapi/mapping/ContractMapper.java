package hu.futureofmedia.task.contactsapi.mapping;

import hu.futureofmedia.task.contactsapi.DTO.ContractDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContractForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contract;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
//@DecoratedWith(ContractMapperImplementation.class)
public interface ContractMapper {
    ContractDTO toContractDto(Contract contract);

    Contract toContract (ContractDTO contractDTO);
}
