package hu.futureofmedia.task.contactsapi.controller;


import groovy.util.logging.Log4j2;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
@Tag(name = "ContactController")
@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/contact")
@Log4j2
public class ContactController {

    private final ContactService contactService;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/foruser")
    @PreAuthorize("hasAuthority('LIST')")
    public List<ContactForListDTO> findTenForUser(@RequestParam("pageNo") int pageNo){
        logger.info("10 Contact/page GetMapping");
        return contactService.findTenForUser(pageNo);
    }

    @GetMapping("/foruser/{id}")
    @PreAuthorize("hasAuthority('LIST')")
    public ContactDTO findContactById(@PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("single Contact GetMapping");
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public void delete(@PathVariable Long id) throws SQLException {
        logger.info("single Contact DeleteMapping");
        contactService.delete(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE')")
    public Long createContractor(@Valid @RequestBody ContactDTO contactDTO){
        logger.info("single Contact PostMapping");
        return contactService.save(contactDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('MODIFY')")
    public Long updateContactor(@PathVariable ("id") Long id,@Valid @RequestBody ContactDTO contactDTO) throws SQLException {
        logger.info("single Contact PutMapping");
        return contactService.update(id,contactDTO);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAuthority('GET_USER_DATA')")
    public UserDetails findRegistratedUser(@PathVariable ("username") String username){
        logger.info("find registered user // just ADMIN ROLE");
        return userDetailsService.loadUserByUsername(username);
    }

    @DeleteMapping ("/user/{username}")
    @PreAuthorize("hasAuthority('GET_USER_DATA')")
    public void delete(@PathVariable ("username") String username) throws SQLException {
        logger.info("Delete user ");
        userService.delete(username);
    }
}
