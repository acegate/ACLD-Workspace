package com.example.company.domain.key;


import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserKey implements Serializable {

    private Long user_no;
    private String email;


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
