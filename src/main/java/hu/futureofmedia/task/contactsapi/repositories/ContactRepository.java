package hu.futureofmedia.task.contactsapi.repositories;

import antlr.collections.List;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contact, Long> {
   // public Pageable<ContactForListDTO> findContactsByStatus(Status status, Pageable pageable);
}
