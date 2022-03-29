package hu.futureofmedia.task.contactsapi.DTO;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.validator.ContactNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
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
    private Company company;
    private String comment;
    private Status status;
    private ZonedDateTime createDate;
    private ZonedDateTime lastModify;
}
