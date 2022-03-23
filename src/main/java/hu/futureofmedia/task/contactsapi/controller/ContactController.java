package hu.futureofmedia.task.contactsapi.controller;

import com.google.i18n.phonenumbers.NumberParseException;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;

import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController {

    private ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts/{pageNo}")
    public List<ContactForListDTO> findTenForUser(@PathVariable("pageNo") int pageNo){
        return contactService.findTenForUser(pageNo);
    }

    @GetMapping("/contacts/singlepage/{id}")
    public ResponseEntity<ContactDTO> findContactById(@PathVariable("id") Long id) throws RecordNotFoundException {
        return contactService.findById(id);
    }

    @DeleteMapping("/contacts/singlepage/detele/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }

    @PostMapping(value="/contacts/singlepage")
    public ResponseEntity createContractor(@Valid @RequestBody ContactDTO contactDTO) throws NumberParseException {
        if(contactService.validatePhoneNumber(contactDTO.getPhoneNumber())) {
            contactService.save(contactDTO);
            return ResponseEntity.status((HttpStatus.CREATED)).body("jooo");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't create contactor");
    }
    @PutMapping("/contacts/singlepage/modify/{id}")
    public ResponseEntity updateContactor(@PathVariable ("id") Long id,@Valid @RequestBody ContactDTO contactDTO) throws NumberParseException {
        if(contactService.validatePhoneNumber(contactDTO.getPhoneNumber())) {
            return contactService.update(id,contactDTO);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't update contactor");
    }
}
