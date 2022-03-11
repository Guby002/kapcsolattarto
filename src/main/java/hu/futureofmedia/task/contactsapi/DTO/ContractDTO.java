package hu.futureofmedia.task.contactsapi.DTO;

import hu.futureofmedia.task.contactsapi.entities.Company;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class ContractDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private String eMail;
    private String phoneNumber;
    private Company company;
    private String comment;
    private Boolean stat;
    private Date createDate;
    private Date lastModify;
}
