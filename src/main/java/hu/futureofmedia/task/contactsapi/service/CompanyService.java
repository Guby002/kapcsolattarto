package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.entities.Company;

import java.util.Optional;

public interface CompanyService {
    Company getById(Long id);
    void save(Company company);
}
