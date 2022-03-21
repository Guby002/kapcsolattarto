package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContactRepository extends JpaRepository<Contact, Long> {
    void deleteById(Long id);
    Optional<Contact> findById(Long id);
}
