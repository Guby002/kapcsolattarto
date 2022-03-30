package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public Company getById(Long id){
        return companyRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }
}
