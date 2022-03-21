package hu.futureofmedia.task.contactsapi.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "second_name")
    @NotBlank
    private String secondName;

    @Column(name = "email")
    @NotBlank
    private String eMail;

    @Column(name="phonenumber")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column
    private String comment;

    @Column(nullable = false)
    private Boolean stat;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModify;

}
