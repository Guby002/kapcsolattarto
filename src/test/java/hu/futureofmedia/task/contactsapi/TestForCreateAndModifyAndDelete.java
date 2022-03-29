package hu.futureofmedia.task.contactsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
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

import org.mockito.Mockito;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


import static java.time.ZonedDateTime.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    public void whenPutRequestToContactorsAndValidContactor_thenCorrectResponse() throws Exception {
        Company company = new Company(1L, "as");
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        ContactDTO contactDTO = new ContactDTO(1L,"Old", "Almos", "ha@hah.hu", "302055441", company, "ha",Status.ACTIVE,  d1, d2);
        ContactDTO modifiedContactDTO = new ContactDTO(1L,"New", "Almos", "ha@hah.hu", "302055441", company, "ha",Status.ACTIVE,  d1, d2);
        String json = objectMapper.writeValueAsString(modifiedContactDTO);
        System.out.println(json);
        contactService.save(contactDTO);

        this.mockMvc.perform(put("/contacts/{id}", 1L)
                .contentType("application/json")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToContactorsAndValidContactor_thenCorrectResponse() throws Exception {
        Company company = new Company(1L, "as");
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        ContactDTO contactDTO = new ContactDTO(1L,"Old", "Almos", "ha@hah.hu", "302055441", company, "ha",Status.ACTIVE,  d1, d2);
        contactService.save(contactDTO);
        String json = objectMapper.writeValueAsString(contactDTO);
        MockHttpServletRequestBuilder builder =
                post("/contacts")
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
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        System.out.println(d1);
        Contact contact = new Contact(1L, "Nagyon", "Almos", "ha@hah.hu", "11231120", company, "ha", Status.ACTIVE, d1, d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/{id}",1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteRequestToContactorsAndValidId_thenCorrectResponse() throws Exception {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company = new Company(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        Contact contact = new Contact(1L, "Nagyon", "Almos", "ha@hah.hu", "1120120", company, "ha", Status.ACTIVE, d1, d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/{id}", 1)).andDo(print())
                .andExpect(status().isOk());
    }

}