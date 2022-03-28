package hu.futureofmedia.task.contactsapi.controller;

import com.google.i18n.phonenumbers.NumberParseException;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;

import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;


    @GetMapping()
    public List<ContactForListDTO> findTenForUser(@RequestParam("pageNo") int pageNo){
        return contactService.findTenForUser(pageNo);
    }

    @GetMapping("/{id}")
    public ContactDTO findContactById(@PathVariable("id") Long id) throws RecordNotFoundException {
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createContractor(@Valid @RequestBody ContactDTO contactDTO, BindingResult result, Model m){
        if(result.hasErrors()) {
            return result.getFieldErrors().toString();
        }
        m.addAttribute("message", "Successfully saved phone: "
                + contactDTO.toString());
        contactService.save(contactDTO);
        return "phoneHome";
    }

    @PutMapping("{id}")
    public Long updateContactor(@PathVariable ("id") Long id,@Valid @RequestBody ContactDTO contactDTO) throws NumberParseException {
           return contactService.update(id,contactDTO);
    }
}
