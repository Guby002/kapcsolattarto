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
@Table(name="user")
public class User {
  @Id
  private Long id;
  @Column(name="userName")
  private String userName;
  @Column(name="password")
  private String password;
  @Column(name = "role")
  private Role role;
}