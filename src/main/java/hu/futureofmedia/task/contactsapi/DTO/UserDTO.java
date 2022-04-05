package hu.futureofmedia.task.contactsapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "validation.required.user.username")
    private String userName;
    @NotBlank(message = "validation.required.user.password")
    private String password;
}
