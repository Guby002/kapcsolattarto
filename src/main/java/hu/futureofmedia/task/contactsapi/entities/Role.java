package hu.futureofmedia.task.contactsapi.entities;

import com.google.common.collect.Sets;


import java.util.Set;

import static hu.futureofmedia.task.contactsapi.entities.Permission.*;


public enum Role {
            USER (Sets.newHashSet()),
    ADMIN(Sets.newHashSet(CONTACTOR_WRITE, CONTACTOR_READ,USER_READ,USER_WRITE));
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }
}
