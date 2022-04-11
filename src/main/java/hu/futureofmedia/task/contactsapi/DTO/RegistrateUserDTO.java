package hu.futureofmedia.task.contactsapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrateUserDTO {
    private String username;
    private String password;
    private String email;
}