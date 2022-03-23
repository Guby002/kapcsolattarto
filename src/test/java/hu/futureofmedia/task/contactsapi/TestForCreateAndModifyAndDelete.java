package hu.futureofmedia.task.contactsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.controller.ContactController;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapperImpl;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
/*
   @Test
    void whenValidInput_thenReturns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
       ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        ContactDTO contactDTO = new ContactDTO(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",true,d1,d2);
        String json = objectMapper.writeValueAsString(contactDTO);
        System.out.println(json);
       Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);
       contactService.save(contractMapper.toContactDto(contact));
      doNothing().when(contactService).save(contactDTO);
       this.mockMvc.perform( MockMvcRequestBuilders .put("/contacts/singlepage/modify/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json))

                .andDo(print())
                .andExpect(
                        MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
   }

    @Test
    public void modifyOneEntityTestController() throws Exception {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.put("/contacts/singlepage/modify{id}",1))
                .andExpect(status().isOk());
    }*/
    //JOOOO
    @Test
    public void getOneEntityTestController() throws Exception {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/singlepage/{id}",1))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteTestController() throws Exception {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);
        contactService.save(contractMapper.toContactDto(contact));
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/singlepage/detele/{id}",1))
                .andExpect(status().isOk());
    }
    @Test
    void phoneNumberValidationNotANumber() throws Exception {
        String s1 ="a";
        assertThrows(NumberParseException.class, () -> {
            contactService.validatePhoneNumber(s1);
        });
    }
    @Test
    void phoneNumberValidation() throws Exception {
        String s1 ="302055441";
        assertTrue(contactService.validatePhoneNumber(s1));
    }
}
