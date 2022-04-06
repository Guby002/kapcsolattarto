package hu.futureofmedia.task.contactsapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name="users")
public class User {
  @Id
  private Long id;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "second_name")
  private String secondName;
  @Column(name="userName")
  private String userName;
  @Column(name="password")
  private String password;
  @Column(name="email")
  private String email;
  @Column(name = "role")
  private Role role;
}