package hu.futureofmedia.task.contactsapi;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.testng.AssertJUnit.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestForGrapgQl {
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
    @WithMockUser(authorities = "MODIFY" )
    @Test
    public void createContactTest() throws Exception {
        String json = ("mutation{\n" +
                "    newContact(\n" +
                "        firstName: \"laci\"\n" +
                "        secondName: \"feri\"\n" +
                "        email: \"feri@feri.g\"\n" +
                "        phoneNumber: \"12345678\"\n" +
                "        companyId: 1\n" +
                "    )\n" +
                "}");
        this.mockMvc.perform(post("/api/graph/")
                        .contentType("application/json")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @WithMockUser(authorities = "MODIFY" )
    @Test
    public void updateContactTest() throws Exception {
        String json = ("mutation{\n" +
                "    updateContact(\n" +
                "         id: 1\n" +
                "        firstName: \"laci\"\n" +
                "        secondName: \"feri\"\n" +
                "        email: \"feri@feri.g\"\n" +
                "        phoneNumber: \"12345678\"\n" +
                "        companyId: 1\n" +
                "    )\n" +
                "}");
        this.mockMvc.perform(post("/api/graph/")
                        .contentType("application/json")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @WithMockUser(authorities = "MODIFY" )
    @Test
    public void deleteContactTest() throws Exception {
        String json = ("mutation{\n" +
                "    deleteContact(id:1)\n" +
                "}");
        this.mockMvc.perform(post("/api/graph/")
                        .contentType("application/json")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @WithMockUser(authorities = "MODIFY" )
    @Test
    public void getTenContact() throws Exception {
        String json = ("query{\n" +
                "    findTenForUser(pageNo: 1){\n" +
                "             id\n" +
                "        firstName\n" +
                "    }\n" +
                "}");
        this.mockMvc.perform(post("/api/graph/")
                        .contentType("application/json")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @WithMockUser(authorities = "MODIFY" )
    @Test
    public void getContact() throws Exception {
        String json = ("query{\n" +
                "   contact(id: 1){\n" +
                "}");
        this.mockMvc.perform(post("/api/graph/")
                        .contentType("application/json")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
