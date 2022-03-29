package hu.futureofmedia.task.contactsapi.controller;


import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;

import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Long createContractor(@Valid @RequestBody ContactDTO contactDTO){
        return contactService.save(contactDTO);
    }

    @PutMapping("{id}")
    public Long updateContactor(@PathVariable ("id") Long id,@Valid @RequestBody ContactDTO contactDTO) {
        return contactService.update(id,contactDTO);
    }
}
