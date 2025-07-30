package com.sbmicroservices.libs.counter;


import com.sbmicroservices.libs.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "counters")
public class Counter extends BaseEntity {


    private String name;

    private Long sequence = 0L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
}
