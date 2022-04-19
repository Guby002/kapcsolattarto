package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.CompanyDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public interface CompanyService {
    @NotNull(message = "validation.required.company") @Valid Company getById(Long id);
    void save(Company company);
}
