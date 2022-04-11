package hu.futureofmedia.task.contactsapi.entities;

import lombok.Getter;

import java.util.Collection;

public enum Permission{
    CONTACTOR_READ("contactor_read"),
    CONTACTOR_WRITE("contactor_write"),
    USER_READ("user_read"),
    USER_WRITE("user_write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
    public String getPermission(){
        return permission;
    }
}
