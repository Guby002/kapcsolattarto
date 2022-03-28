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
    //vissza id
    public void save(ContactDTO contactDTO){
        contactRepository.save(contactMapper.updateContactFromContactDTO(contactDTO)).setStatus(Status.ACTIVE);
    }

    @Override
    @PreUpdate
    public Long update(Long id , ContactDTO contactDTO) {
        if(contactRepository.findById(id).isPresent()) {
            //++mapper update
            contactRepository.save(contactMapper.updateContactFromContactDTO(contactDTO)).setStatus(Status.ACTIVE);

        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if(contactRepository.findById(id).isPresent()) {
            contactRepository.getById(id).setStatus(Status.DELETED);
        }
    }
    @Override
    public List<ContactForListDTO> findTenForUser(int pageNo) {
        if(pageNo< 1 ) pageNo=1;
        Pageable pageSortByName = PageRequest.of(pageNo, 10,Sort.by("firstName"));
        Page <Contact> p = contactRepository. findAllByStatus(Status.ACTIVE,pageSortByName);
        return contactMapper.toContactForListListDto(p.stream().collect(Collectors.toList()));
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
