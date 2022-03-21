package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapperImpl;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestForCreateAndModify {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ContactService contactService;
    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    private static final String API_ROOT
            = "http://localhost:8080/addContact";
    private ContactDTO createRandomContactor() throws ParseException {
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);
        return contractMapper.toContactDto(contact);
    }
    @Test public void whenCreateContactThenCreated() throws Exception {
        mockMvc.perform(post("/page/addContact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"firstName\": \"string\",\n" +
                                "  \"secondName\": \"string\",\n" +
                                "  \"email\": \"feri@feri.com\",\n" +
                                "  \"phoneNumber\": \"1212121212\",\n" +
                                "  \"company\": {\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"string\"\n" +
                                "  },\n" +
                                "  \"comment\": \"string\",\n" +
                                "  \"createDate\": \"2022-03-21T11:00:07.226Z\",\n" +
                                "  \"lastModify\": \"2022-03-21T11:00:07.226Z\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("feri@feri.com"));

    }
}
