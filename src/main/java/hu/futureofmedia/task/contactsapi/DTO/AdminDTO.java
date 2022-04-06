package hu.futureofmedia.task.contactsapi.DTO;

import hu.futureofmedia.task.contactsapi.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
        @Id
        private Long id;
        @Column(name = "created_date", nullable = false)
        private ZonedDateTime createDate;
        private String firstName;
        @NotBlank(message = "validation.required.second-name")
        private String secondName;
        @NotBlank(message = "validation.required.user-name")
        private String userName;
        @NotBlank(message = "validation.required.password")
        private String password;
        @NotBlank(message = "validation.required.e-mail")
        @Email(message = "validation.required.e-mail.needed")
        private String email;
        private Role role;

}

