package hu.futureofmedia.task.contactsapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.futureofmedia.task.contactsapi.entities.Role;
import lombok.*;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "validation.required.user.username")
    private String username;
    @NotBlank(message = "validation.required.user.password")
    private String password;
    @Email(message = "validation.required.user.mail")
    private String email;
    @NotBlank(message = "validation.required.user.role")
    private Set<Role> roles = new HashSet<>();
}
