package hu.futureofmedia.task.contactsapi;
/*
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import hu.futureofmedia.task.contactsapi.DTO.CompanyDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

import static java.time.ZonedDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private ContactRepository contactRepository;

    @Test
    public void getById() throws IOException {
        Company company = new Company(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        Contact contact = new Contact(1L, "Nagyon", "Almos", "ha@hah.hu", "11231120", company, "ha", Status.ACTIVE, d1,d2);
                when(contactRepository.findById(any()))
                .thenReturn(Optional.of(contact));

        GraphQLResponse response =
                graphQLTestTemplate.postForResource("get-pokemon-by-id.graphql");

        assertTrue(response.isOk());
        assertEquals("1", response.get("$.data.pokemon.id"));
        assertEquals("Pikachu", response.get("$.data.pokemon.name"));
    }
}
*/