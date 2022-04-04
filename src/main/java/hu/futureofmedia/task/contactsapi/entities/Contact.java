package hu.futureofmedia.task.contactsapi.entities;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name="contact")
public class Contact{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "email")
    String email;

    @Column(name="phonenumber")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column
    private String comment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createDate;;

    @Column(name = "last_modified_date_time", nullable = false)
    private ZonedDateTime lastModify;

    @PreUpdate
    protected void setNewLastModifiedDate() {
        this.lastModify=ZonedDateTime.now();
    }
    @PrePersist
    protected void  setCreateDateTime(){
        this.createDate=ZonedDateTime.now();
    }

}
