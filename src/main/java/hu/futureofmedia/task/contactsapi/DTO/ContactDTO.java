package hu.futureofmedia.task.contactsapi.DTO;

import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.validator.ContactNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private Long id;
    @NotBlank(message = "validation.required.first-name")
    private String firstName;
    @NotBlank(message = "validation.required.second-name")
    private String secondName;
    @NotBlank(message = "validation.required.e-mail")
    @Email(message = "validation.required.e-mail.needed")
    private String email;
    @ContactNumberConstraint
    private String phoneNumber;
    @NotNull(message = "validation.required.company")
    @Valid
    private Long companyId;
    private String comment;
    private Status status;
    private ZonedDateTime createDate;
    private ZonedDateTime lastModify;
}
