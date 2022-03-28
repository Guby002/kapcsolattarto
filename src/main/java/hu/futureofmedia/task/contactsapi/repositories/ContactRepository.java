package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findAllByStatus(Status status, Pageable pageable);
    Page<Contact> findAll(Pageable pageable);
}
