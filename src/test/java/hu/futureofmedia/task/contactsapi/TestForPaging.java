package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.DTO.CompanyDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapperImpl;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZonedDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TestForPaging {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ContactService contactService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testGetExample() throws Exception {
        List<ContactForListDTO> contracts = new ArrayList<>();
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        CompanyDTO company = new CompanyDTO(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        ContactDTO contactDTO = new ContactDTO(1L, "Nagyon", "Almos", "ha@hah.hu", "1120120", company, "ha", Status.ACTIVE, d1, d2);
        Contact contact=contractMapper.toContact(contactDTO);
        contracts.add(contractMapper.toContactForListDto(contact));
        contactService.save(contractMapper.toContactDto(contact));
        Mockito.when(contactService.findTenForUser(1)).thenReturn(contracts);
        mockMvc.perform(get("/contacts").param("pageNo", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("Nagyon Almos"));

    }

    @Test
    public void testGetExampleFalse() throws Exception {
        List<ContactForListDTO> contracts = new ArrayList<>();
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company = new Company(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        Contact contact = new Contact(1L, "Nagyon", "Almos", "ha@hah.hu", "1120120", company, "ha", Status.DELETED, d1, d2);

        contracts.add(contractMapper.toContactForListDto(contact));
        MvcResult result = mockMvc.perform(get("/contacts").param("pageNo", "1"))
                .andExpect(status().isOk())
                .andReturn();
        String actualJson = result.getResponse().getContentAsString();
        assertEquals("[]", actualJson);

    }

    @Test
    public void testForSecondPageExample() throws Exception {
        CompanyDTO company = new CompanyDTO(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        ContactDTO contact ;
        List <ContactDTO> n= new ArrayList<>();
       for (Long l = 1L; l < 11L; l++) {
            contact = new ContactDTO(l, "Nagyon", "Almos", "ha@hah.hu", "1120120", company, "ha", Status.ACTIVE, d1, d2);
            contactService.save(contact);
            n.add(contactService.findById(l));
        }
       //V a sorrend miatt
        contact = new ContactDTO(11L, "Vocis", "Almos", "ha@hah.hu", "1120120", company, "ha", Status.ACTIVE, d1, d2);
        contactService.save(contact);
        n.add(contact);
        contact = new ContactDTO(12L, "Vocis", "Almos", "ha@hah.hu", "1120120", company, "ha", Status.ACTIVE, d1, d2);
        contactService.save(contact);
        n.add(contact);
        System.out.println(n.size());
        mockMvc.perform(get("/contacts").param("pageNo", "2")
                .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
               .andExpect(jsonPath("$[0].id").value(11L));
    }
}
