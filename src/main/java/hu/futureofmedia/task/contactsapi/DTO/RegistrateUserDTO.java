package hu.futureofmedia.task.contactsapi.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RegistrateUserDTO {
    @NotBlank(message = "validation.required.user.username")
    private String username;
    @NotBlank(message = "validation.required.user.password")
    private String password;
    @Email(message = "validation.required.user.mail")
    private String email;
}
