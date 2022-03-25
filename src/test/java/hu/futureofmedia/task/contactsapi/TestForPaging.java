package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.DTO.ContactForListDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapperImpl;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @MockBean
    private ContactService contactService;
    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
 /*   @Test
    public void testGetExample() throws Exception {
        List<ContactForListDTO> contracts = new ArrayList<>();
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",true,d1,d2);

        contracts.add(contractMapper.toContactForListDto(contact));
        Mockito.when(contactService.findTenForUser(1)).thenReturn(contracts);
        mockMvc.perform(get("/contacts/{pageNo}",1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].Name").value("Nagyon Almos"));

    }
    @Test
    public void testGetExampleFalse() throws Exception {
        List<ContactForListDTO> contracts = new ArrayList<>();
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact = new Contact(1L,"Nagyon","Almos","ha@hah.hu","1120120",company,"ha",false,d1,d2);

        contracts.add(contractMapper.toContactForListDto(contact));
        MvcResult result = mockMvc.perform(get("/contacts/{pageNo}",1))
                .andExpect(status().isOk())
                .andReturn();
        String actualJson =result.getResponse().getContentAsString();
        assertEquals("[]", actualJson);

    }
    @Test
    public void testForSecondPageExample() throws Exception {
        List<ContactForListDTO> contracts = new ArrayList<>();
        ContactMapperImpl contractMapper = new ContactMapperImpl();
        Company company=new Company(1L, "as");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-20");
        Contact contact= new Contact();
        for(long l=0;l<10;l++) {
            contact = new Contact(l, "Nagyon", "Almos", "ha@hah.hu", "1120120", company, "ha", true, d1, d2);
            contracts.add(contractMapper.toContactForListDto(contact));
        }
            contact = new Contact(11L, "Focis", "Almos", "ha@hah.hu", "1120120", company, "ha", true, d1, d2);
            contracts.add(contractMapper.toContactForListDto(contact));
            contact = new Contact(12L, "Focis", "Almos", "ha@hah.hu", "1120120", company, "ha", true, d1, d2);
        contracts.add(contractMapper.toContactForListDto(contact));
        Mockito.when(contactService.findTenForUser(2)).thenReturn(contracts);
        mockMvc.perform(get("/contacts/{pageNo}",2))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[11].Name").value("Focis Almos"));
    }
*/
}
