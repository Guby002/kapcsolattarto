package hu.futureofmedia.task.contactsapi.controller;

import groovy.util.logging.Log4j2;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts/user")
@Log4j2
public class UserController {
    private final UserService userService;
    private final ContactService contactService;

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable("userId") Long userId){
        return userService.getUserById(userId);
    }


    @GetMapping()
    public List<ContactForListDTO> findTenForUser(@RequestParam("pageNo") int pageNo){
        return contactService.findTenForUser(pageNo);
    }

    @GetMapping("/{id}")
    public ContactDTO findContactById(@PathVariable("id") Long id) throws RecordNotFoundException {
        return contactService.findById(id);
    }
}
