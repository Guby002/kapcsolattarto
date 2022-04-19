package hu.futureofmedia.task.contactsapi.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {
    @NotBlank(message = "validation.required.user.username")
    private String username;
    @NotBlank(message = "validation.required.user.password")
    private String password;
    @Email(message = "validation.required.user.mail")
    private String email;
}
