package hu.futureofmedia.task.contactsapi.controller;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {
    private ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/page/{pageNo}")
    public List<ContactForListDTO> findTenForUser(@PathVariable int pageNo){
        return contactService.findTenForUser(pageNo);
    }

    @GetMapping("page/{id}")
    public ContactDTO findContactById(@PathVariable Long id){
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
    /*
    @PostMapping("listpage/addContract")
    public ResponseEntity<ContactDTO> createContractor(@RequestBody ContactDTO contractDTO){
        contactService.save(contractMapper);
        return ResponseEntity.status(HttpStatus.CREATED).body(contractDTO);
    }*/
}
