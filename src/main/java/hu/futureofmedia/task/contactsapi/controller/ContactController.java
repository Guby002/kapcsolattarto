package hu.futureofmedia.task.contactsapi.controller;


import groovy.util.logging.Log4j2;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.entities.RoleName;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/contact")
@Log4j2
public class ContactController {

    private final ContactService contactService;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    Logger logger = LoggerFactory.getLogger(ContactController.class);
    @GetMapping()
    public List<ContactForListDTO> findTenForUser(@RequestParam("pageNo") int pageNo){
        logger.info("10 Contact/page GetMapping ");
        return contactService.findTenForUser(pageNo);
    }
    @GetMapping("/{id}")
    public ContactDTO findContactById(@PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("single Contact GetMapping");
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws SQLException {
        logger.info("single Contact DeleteMapping");
        contactService.delete(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long createContractor(@Valid @RequestBody ContactDTO contactDTO){
        logger.info("single Contact PostMapping");
        return contactService.save(contactDTO);
    }

    @PutMapping("{id}")
    public Long updateContactor(@PathVariable ("id") Long id,@Valid @RequestBody ContactDTO contactDTO) throws SQLException {
        logger.info("single Contact PutMapping");
        return contactService.update(id,contactDTO);
    }
    @GetMapping("/user/{username}")
    public UserDTO findRegistratedUser(@PathVariable ("username") String username){
        logger.info("find registered user // just ADMIN ROLE");
        return userMapper.userDetailsToUserDTO(userDetailsService.loadUserByUsername(username));
    }
}
