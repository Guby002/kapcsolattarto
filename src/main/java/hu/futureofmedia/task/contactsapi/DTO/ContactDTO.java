package hu.futureofmedia.task.contactsapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.entities.Company;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class ContactDTO {
    @JsonIgnore
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private Company company;
    private String comment;
    @JsonIgnore
    private Boolean stat;
    private Date createDate;
    private Date lastModify;

}
