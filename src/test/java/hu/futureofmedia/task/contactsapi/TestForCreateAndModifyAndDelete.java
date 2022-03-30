package hu.futureofmedia.task.contactsapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.futureofmedia.task.contactsapi.DTO.CompanyDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.service.CompanyService;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;


import static java.time.ZonedDateTime.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestForCreateAndModifyAndDelete {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        companyService.save(new Company(1L,"lalala"));
    }
    public ContactDTO createForTest (){
        CompanyDTO company = new CompanyDTO(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        System.out.println(d1);
        ContactDTO contactDTO = new ContactDTO(1L, "Nagyon", "Almos", "ha@hah.hu", "11231120", company, "ha", Status.ACTIVE, d1, d2);
        return contactDTO;
    }
    @Test
    public void whenPutRequestToContactorsAndValidContactor_thenCorrectResponse() throws Exception {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ContactDTO contactDTO = createForTest();
        ContactDTO modifiedContactDTO = createForTest();
        modifiedContactDTO.setFirstName("Lali");
        String json = objectMapper.writeValueAsString(modifiedContactDTO);
        contactService.save(contactDTO);
       this.mockMvc.perform(put("/contacts/{id}", 1L)
                .contentType("application/json")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$").value(1));

    }//asserequals with strings objecmapper.readvalue

    @Test
    public void whenPostRequestToContactorsAndValidContactor_thenCorrectResponse() throws Exception {
        ContactDTO contactDTO = createForTest();
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
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
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$").value(1));
    //    assertEquals("hadsadsh,",s);
    }

    @Test
    public void whenGetRequestToContactorsAndValidId_thenCorrectResponse() throws Exception {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
      contactService.save(createForTest());
        MvcResult result =  mockMvc.perform(get("/contacts/1")
                .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value("Nagyon"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
    }///assert equal


    @Test
    public void whenDeleteRequestToContactorsAndValidId_thenCorrectResponse() throws Exception {
        contactService.save(createForTest());
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/{id}",1))
                .andDo(print())
                .andExpect(status().isOk());

    }
    ///SHOUD throw exception
    @Test
    public void whenPostRequestToContactorsAndValidContactor_thenThrowException_CauseEmptyFirstName() throws Exception {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ContactDTO contactDTO =createForTest();
        contactDTO.setFirstName("");
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
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.errors").value("firstName: validation.required.first-name"));

    }
    @Test
    public void whenPostRequestToContactorsAndValidContactor_thenThrowException_CauseEmptySecondName()throws Exception{
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ContactDTO contactDTO =createForTest();
        contactService.save(contactDTO);
        contactDTO.setSecondName("");
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
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.errors").value("secondName: validation.required.second-name"));
    }
    @Test
    public void whenPostRequestToContactorsAndValidContactor_thenThrowException_CauseWrongPhoneNumber() throws Exception {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ContactDTO contactDTO =createForTest();
        contactDTO.setPhoneNumber("1234121");
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
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.errors").value("phoneNumber: Invalid phone number"));
    }
}