package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.entities.User;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import hu.futureofmedia.task.contactsapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ContactForListDTO> findTenForUser(int pageNo) {
        if( pageNo< 1 ) {
            pageNo=1;
        }
        Pageable pageSortByName = PageRequest.of(pageNo-1 ,10, Sort.by("firstName"));
        Page<Contact> listData = contactRepository.findAllByStatus(Status.ACTIVE,pageSortByName);
        return listData.stream().map(contactMapper::toContactForListDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDTO findById (Long id) {
        Contact contact = findContact(id);
        return contactMapper.toContactDto(contact);
    }

    private Contact findContact (Long id){
        return contactRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public UserDTO getUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        return userMapper.userToUserDTO(user);
    }
}
