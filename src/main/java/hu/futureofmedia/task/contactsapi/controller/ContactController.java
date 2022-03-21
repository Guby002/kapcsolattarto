package hu.futureofmedia.task.contactsapi.controller;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {
    private ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("listpage/{pageNo}")
    public List<ContactForListDTO> findTenForUser(@PathVariable int pageNo){
        return contactService.findTenForUser(pageNo);
    }

    @GetMapping("singleuser/{id}")
    public ContactDTO findContactById(@PathVariable Long id){
        return contactService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }

    @PostMapping("page/addContact")
    public ResponseEntity<ContactDTO> createContractor(@RequestBody ContactDTO contactDTO){
        contactService.save(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactDTO);
    }
}
