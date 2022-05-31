package hu.futureofmedia.task.contactsapi.service;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.DTO.MailDTO;
import hu.futureofmedia.task.contactsapi.controller.ContactController;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.jms.JmsProducer;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService  {
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;
    private final CompanyService companyService;
    private final JmsProducer jmsProducer;
    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Override
    @Transactional(readOnly = false)
    public Long save(ContactDTO contactDTO){
        contactDTO.setStatus(Status.ACTIVE);
        Contact contact = contactMapper.toContact(contactDTO);
        contact.setCompany(companyService.getById(contactDTO.getCompanyId()));
        var id = contactRepository.save(contact).getId();
        logger.debug("New contactor saved ,contact:{}" ,contact);
        jmsProducer.send(new MailDTO(contact.getCompany().getName(),contact.getEmail()));
        return id;
    }



    @Override
    @Transactional
    public Long update(Long id , ContactDTO contactDTO){
        Contact contact = findContact(id);
        contactMapper.updateContactFromContactDTO(contactDTO,contact);
        contact.setCompany(companyService.getById(contactDTO.getCompanyId()));
        contactRepository.save(contact);
        logger.debug("contactDTO: {},contact: {}",contactDTO,contact);
        return id;
    }

    @Override
    @Transactional
    public void delete(Long id){
        Contact contact = findContact(id);
        contact.setStatus(Status.DELETED);
        contactRepository.save(contact);
        logger.debug("Contactor deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactForListDTO> findTenForUser(int pageNo) {
        if( pageNo< 1 ) {
            pageNo=1;
        }
        logger.debug(pageNo+"Find 10 contactor for a page");
        Pageable pageSortByName = PageRequest.of(pageNo-1 ,10,Sort.by("firstName"));
        Page<Contact> listData = contactRepository.findAllByStatus(Status.ACTIVE,pageSortByName);

        logger.info(listData.toList().toString());
        return listData.stream().map(contactMapper::toContactForListDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDTO findById (Long id) {
        Contact contact = findContact(id);
        logger.debug("Find contact by Id");
        return contactMapper.toContactDto(contact);
    }

    @Transactional(readOnly = true)
    public Contact findContact(Long id){
        logger.debug("Search for one contact by Id");
        return contactRepository.findContactById(id).orElseThrow(RecordNotFoundException::new);
    }
}
