package ru.otus.laboratory.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {

    private long id;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }
}
