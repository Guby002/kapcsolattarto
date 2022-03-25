package hu.futureofmedia.task.contactsapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.validator.ContactNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class ContactDTO {
    //validáció ide kell!
    @NotBlank(message = "validation.required.first-name")
    private String firstName;
    @NotBlank(message = "validation.required.second-name")
    private String secondName;
    @NotBlank(message = "validation.required.e-mail")
    private String email;
    @ContactNumberConstraint
    private String phoneNumber;
    private Company company;
    private String comment;
    private ZonedDateTime createDate;
    private ZonedDateTime lastModify;
}
