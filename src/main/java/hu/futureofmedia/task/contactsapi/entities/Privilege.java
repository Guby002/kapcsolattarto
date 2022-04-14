package hu.futureofmedia.task.contactsapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PrivilegeName name;
    @ManyToMany(fetch = FetchType.EAGER , mappedBy ="privileges")
    private Set<Role> roles = new HashSet<>();
}