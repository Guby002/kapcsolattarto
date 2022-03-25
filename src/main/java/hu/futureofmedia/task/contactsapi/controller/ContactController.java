package hu.futureofmedia.task.contactsapi.controller;

import com.google.i18n.phonenumbers.NumberParseException;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;

import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> createContractor(@RequestBody ContactDTO contactDTO) throws NumberParseException {
        if(ContactMapper.validatePhoneNumber(contactDTO.getPhoneNumber())){
            contactService.save(contactDTO);
            return ResponseEntity.status((HttpStatus.CREATED)).body("Contactor created");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't create contactor");
    }
    @PutMapping("/contacts/singlepage/modify/{id}")
    public ResponseEntity<String> updateContactor(@PathVariable ("id") Long id,@Valid @RequestBody ContactDTO contactDTO) throws NumberParseException {
        if(ContactMapper.validatePhoneNumber(contactDTO.getPhoneNumber())){
            contactService.update(id,contactDTO);
            return ResponseEntity.ok("Contact Updated");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't update contactor");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
