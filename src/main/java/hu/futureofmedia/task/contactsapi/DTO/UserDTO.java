package hu.futureofmedia.task.contactsapi.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    @NotBlank(message = "validation.required.first-name")
    private String firstName;
    @NotBlank(message = "validation.required.second-name")
    private String secondName;
    @NotBlank(message = "validation.required.user.username")
    private String username;
    @NotBlank(message = "validation.required.user.password")
    private String password;
    @Email(message = "validation.required.user.mail")
    private String email;
}
