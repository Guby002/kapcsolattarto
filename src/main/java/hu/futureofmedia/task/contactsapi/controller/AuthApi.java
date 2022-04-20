package hu.futureofmedia.task.contactsapi.controller;

import hu.futureofmedia.task.contactsapi.DTO.LoginDTO;
import hu.futureofmedia.task.contactsapi.DTO.RegisterUserDTO;
import hu.futureofmedia.task.contactsapi.entities.JwtResponse;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectEmailException;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectUserNameException;
import hu.futureofmedia.task.contactsapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Authentication")
@RestController
@RequestMapping(path = "api/auth")
@RequiredArgsConstructor
public class AuthApi {

    protected final Log logger = LogFactory.getLog(getClass());
    public final UserService userService;

    @PostMapping(value = "/login")
    public JwtResponse login(@Valid @RequestBody LoginDTO loginDTO) {
        logger.info("Logging in");
        return userService.login(loginDTO);
    }
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterUserDTO request) throws IncorrectEmailException, IncorrectUserNameException {
        logger.info("User registration");
        return userService.userRegistration(request);
    }
}
