package hu.futureofmedia.task.contactsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapperImpl;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;


import static java.time.ZonedDateTime.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class TestForCreateAndModifyAndDelete {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ContactService contactService;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

  /*  @Test
    public void whenPutRequestToContactorsAndValidContactor_thenCorrectResponse() throws Exception {
        Company company = new Company(1L, "as");
        ObjectMapper objectMapper = new ObjectMapper();
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        ContactDTO contactDTO = new ContactDTO(1L, "AJ", "Almos", "ha@hah.hu", "302055441", company, "ha", true, d1, d2);
        ContactDTO modifiedContactDTO = new ContactDTO(2L, "New", "Almos", "ha@hah.hu", "302055441", company, "ha", true, d1, d2);
        String json = objectMapper.writeValueAsString(modifiedContactDTO);
        System.out.println(json);
        contactService.save(contactDTO);

        this.mockMvc.perform(put("/contacts/singlepage/modify/{id}", 1L)
                .contentType("application/json")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
*/
    @Test
    public void whenPostRequestToContactorsAndValidContactor_thenCorrectResponse() throws Exception {
        Company company = new Company(1L, "as");
        ObjectMapper objectMapper = new ObjectMapper();
        ZonedDateTime d2 = ZonedDateTime.now();
        ZonedDateTime d1 = ZonedDateTime.now();
        ContactDTO contactDTO = new ContactDTO( "Nagyon", "Almos", "ha@hah.hu", "1á120", company, "ha", d2, d1);
        String json = objectMapper.writeValueAsString(contactDTO);
        MockHttpServletRequestBuilder builder =
                post("/contacts/singlepage")
                        .contentType("application/json")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void whenGetRequestToContactorsAndValidId_thenCorrectResponse() throws Exception {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company = new Company(1L, "as");
        ZonedDateTime d2 = ZonedDateTime.now();
        ZonedDateTime d1 = ZonedDateTime.now();
        System.out.println(d1);
        Contact contact = new Contact(1L, "Nagyon", "Almos", "ha@hah.hu", "1á120", company, "ha", Status.ACTIVE, d1, d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/{id}", 1))
                .andExpect(status().isOk());
    }
/*
    @Test
    public void whenDeleteRequestToContactorsAndValidId_thenCorrectResponse() throws Exception {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company = new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L, "Nagyon", "Almos", "ha@hah.hu", "1120120", company, "ha", false, d1, d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/singlepage/detele/{id}", 1))
                .andExpect(status().isOk());
    }*/

}