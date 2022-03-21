package hu.futureofmedia.task.contactsapi.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EnableJpaAuditing
@Data
@Table(name="contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "Keresztnév kötelező")
    private String firstName;

    @Column(name = "second_name")
    @NotBlank(message = "Vezetéknév kötelező")
    private String secondName;

    @Column(name = "email")
    @NotBlank(message = "E-mail kötelező")
    private String email;

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
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModify;

}
