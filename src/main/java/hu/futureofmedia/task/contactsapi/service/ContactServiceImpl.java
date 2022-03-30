package hu.futureofmedia.task.contactsapi.service;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.controller.ContactController;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService  {
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;
    private final CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(ContactController.class);
    @Override
    public Long save(ContactDTO contactDTO){
        contactDTO.setStatus(Status.ACTIVE);
        Contact contact = contactMapper.toContact(contactDTO);
        contact.setCompany(companyService.getById(contactDTO.getCompanyDTO().getId()));
        logger.debug("New contactor saved");
        return contactRepository.save(contact).getId();
    }

    @Override
    public Long update(Long id , ContactDTO contactDTO) {
        Contact contact = findContact(id);
        contactMapper.updateContactFromContactDTO(contactDTO,contact);
        contact.setCompany(companyService.getById(contactDTO.getCompanyDTO().getId()));
        contactRepository.save(contact);
        logger.debug("Contactor updated");
        return id;
    }

    @Override
    public void delete(Long id) {
        Contact contact = findContact(id);
        contact.setStatus(Status.DELETED);
        contactRepository.save(contact);
        logger.debug("Contactor deleted");
    }

    @Override
    public List<ContactForListDTO> findTenForUser(int pageNo) {
        if( pageNo< 1 ) {
            pageNo=1;
        }
        logger.debug("Find 10 contactor for a page");
        Pageable pageSortByName = PageRequest.of(pageNo-1 ,10,Sort.by("firstName"));
        Page<Contact> listData = contactRepository.findAllByStatus(Status.ACTIVE,pageSortByName);
        return listData.stream().map(contactMapper::toContactForListDto).collect(Collectors.toList());
    }

    @Override
    public ContactDTO findById (Long id) {
        Contact contact = findContact(id);
        logger.debug("Find contact by Id");
        return contactMapper.toContactDto(contact);
    }
    private Contact findContact (Long id){
        logger.debug("Search for one contact by Id");
        return contactRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }
}
