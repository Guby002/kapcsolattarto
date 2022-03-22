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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static io.restassured.authentication.FormAuthConfig.springSecurity;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TestForCreateAndModifyAndDelete {
    @Autowired
    private MockMvc mockMvc;
   /* @Autowired
    private WebApplicationContext webApplicationContext;*/
    @MockBean
    private ContactService contactService;
    @Autowired
    private ObjectMapper objectMapper;

  /*  @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }*/
    @Test
    void whenValidInput_thenReturns200() throws Exception {
        ContactMapperImpl contactMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);
        ContactDTO contactDTO = contactMapper.toContactDto(contact);
        ContactDTO c = new ContactDTO();
        Mockito.when(contactService.update(1L,contactDTO).getBody());
        mockMvc.perform(put("/contacts/{id}",1)
                        .contentType("application/json"))
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
