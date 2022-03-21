package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ContactMapperTest {
    private static ContactMapper mapper;

    private static PodamFactory podamFactory;
    @BeforeAll
    public static void setUp() {
        podamFactory = new PodamFactoryImpl();

        // to avoid infinite loops creating random data
        DefaultClassInfoStrategy classInfoStrategy = DefaultClassInfoStrategy.getInstance();
        classInfoStrategy.addExcludedField(Contact.class, "phoneNumber");
        classInfoStrategy.addExcludedField(Contact.class, "comment");
        classInfoStrategy.addExcludedField(Contact.class, "stat");

        podamFactory.setClassStrategy(classInfoStrategy);

        mapper = (ContactMapper) new ContactMapperImpl();
    }
    @Test
    void testContractMappertoContractDTO(){

        Contact contact = podamFactory.manufacturePojo(Contact.class);

        ContactDTO contactDTO = mapper.toContactDto(contact);

        assertAll(
                () -> {
                    Assertions.assertEquals(contact.getFirstName(), contactDTO.getFirstName());
                    Assertions.assertEquals(contact.getSecondName(), contactDTO.getSecondName());
                    //if thats 2 true every copy will be okey
                }
        );
    }
    @Test
    void testContractMappertoContract(){

        ContactDTO contactDTO = podamFactory.manufacturePojo(ContactDTO.class);

        Contact contact = mapper.toContact(contactDTO);

        assertAll(
                () -> {
                    Assertions.assertEquals(contact.getFirstName(), contactDTO.getFirstName());
                    Assertions.assertEquals(contact.getSecondName(), contactDTO.getSecondName());
                    //if thats 2 true every copy will be okey

                }
        );
    }
}