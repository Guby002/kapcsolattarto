package hu.futureofmedia.task.contactsapi.DTO;

import hu.futureofmedia.task.contactsapi.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "validation.required.first-name")
    private String firstName;
    @NotBlank(message = "validation.required.second-name")
    private String secondName;
    @NotBlank(message = "validation.required.user.username")
    private String userName;
    @NotBlank(message = "validation.required.user.password")
    private String password;
    @Email(message = "validation.required.user.mail")
    private String email;
    private Role role;
}
