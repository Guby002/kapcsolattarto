package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.DTO.CompanyDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.DTO.UserDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Role;
import hu.futureofmedia.task.contactsapi.entities.RoleName;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.mapper.ContactMapper;
import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import hu.futureofmedia.task.contactsapi.repositories.RoleRepository;
import hu.futureofmedia.task.contactsapi.repositories.UserRepository;
import hu.futureofmedia.task.contactsapi.service.CompanyService;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.time.ZonedDateTime.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactMapper contactMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        creatUser();
    }


    private UserDTO creatUser()
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("feri2");
        userDTO.setPassword("feri2");
        userDTO.setEmail("laci@laci.laci");
        Set<Role> roles =new HashSet<>();
        roles.add(roleRepository.findRoleByName(RoleName.USER));
        userDTO.setRoles(roles);
        return userDTO;
    }
    @Test
    @WithMockUser(authorities = "GET_USER_DATA")
    void withAdminProfileGetUserData() throws Exception {
        this.mockMvc.perform(get("/api/contact/user/admin")
                        .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
               .andExpect(jsonPath("$.username").value("admin"));
    }
    @Test
    @WithMockUser(authorities = "GET_USER_DATA")
    public void whenDeleteRequestToUserAndValidUserName_thenCorrectResponse() throws Exception {
       userRepository.save(userMapper.userDTOToUser(creatUser()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contact/user/feri2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
