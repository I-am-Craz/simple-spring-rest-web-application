package org.example.entities;

public enum Role {
    ROLE_USER("USER"),
    ROLE_MODER("MODER"),
    ROLE_ADMIN("ADMIN");

    private String authority;
    Role(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }
}
