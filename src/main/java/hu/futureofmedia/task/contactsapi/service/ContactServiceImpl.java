package hu.futureofmedia.task.contactsapi.service;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Long save(ContactDTO contactDTO){
       contactRepository.save(contactMapper.updateContactFromContactDTO(contactDTO));
        return 1L      ;
    }
    @Override
    @PreUpdate
    public Long update(Long id , ContactDTO contactDTO) {
        if(contactRepository.findById(id).isPresent()) {
            //++mapper update
            return contactRepository.save(contactMapper.toContact(contactDTO)).getId();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
        //státusz
    }


    @Override
    public List<ContactForListDTO> findTenForUser(int pageNo) {
    //    return contactRepository.findContactsByStatus(Status.ACTIVE,Pageable pageable);
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
