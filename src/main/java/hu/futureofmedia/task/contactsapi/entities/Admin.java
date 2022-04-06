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
@Table(name="admin")
public class Admin {
    @Id
    private Long id;
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createDate;
    @Column(name="userName")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private Role role;
    @PrePersist
    protected void  setCreateDateTime(){
        this.createDate=ZonedDateTime.now();
    }
}
