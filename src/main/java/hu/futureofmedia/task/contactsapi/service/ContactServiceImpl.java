package hu.futureofmedia.task.contactsapi.service;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.PreUpdate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService  {
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;


    @Override
    public Long save(ContactDTO contactDTO){
        contactDTO.setStatus(Status.ACTIVE);
        return contactRepository.save(contactMapper.toContact(contactDTO)).getId();
    }

    @Override
    @PreUpdate
    public Long update(Long id , ContactDTO contactDTO) {
        if(contactRepository.findById(id).isPresent()) {
                Contact contact = contactRepository.getById(id);
                contactMapper.updateContactFromContactDTO(contactDTO,contact);
                contactRepository.save(contact);
                return id;
            }
            return null;
    }

    @Override
    public void delete(Long id) {
        if(contactRepository.findById(id).isPresent()){
            Contact contact = contactRepository.getById(id);
            contact.setStatus(Status.DELETED);
            contactRepository.save(contact);
        }
    }

    @Override
    public List<ContactForListDTO> findTenForUser(int pageNo) {
        if(pageNo< 1 ) pageNo=1;
        Pageable pageSortByName = PageRequest.of(pageNo-1 ,10,Sort.by("firstName"));
        List<Contact> listData = contactRepository.findAllByStatus(Status.ACTIVE,pageSortByName).toList();
        return listData.stream().map(contactMapper::toContactForListDto).collect(Collectors.toList());
    }

    @Override
    public ContactDTO findById (Long id) {
        Optional<ContactDTO> contactDTO = Optional.ofNullable(contactMapper.toContactDto(
                contactRepository.findById(id).orElseThrow(RecordNotFoundException::new)));
        if (contactDTO.isPresent()) {
            return contactDTO.get();
        }
        return null;
    }
}
