package hu.futureofmedia.task.contactsapi.controller;

import hu.futureofmedia.task.contactsapi.mapper.UserMapper;
import hu.futureofmedia.task.contactsapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Tag(name = "UserController")
@RestController
@RequestMapping(path = "api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/{username}")
   // @PreAuthorize("hasAuthority('GET_USER_DATA')")
    public UserDetails findRegistratedUser(@PathVariable("username") String username){
        logger.info("find registered user // just ADMIN ROLE");
        return userDetailsService.loadUserByUsername(username);
    }

    @DeleteMapping("/{username}")
  //  @PreAuthorize("hasAuthority('GET_USER_DATA')")
    public void delete(@PathVariable ("username") String username) throws SQLException {
        logger.info("Delete user ");
        userService.delete(username);
    }
}
