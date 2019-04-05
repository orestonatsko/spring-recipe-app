package com.springframework.domain;

import javax.persistence.*;

@Table(name = "difficulty")
@Entity
public class TableDifficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficulty_id", columnDefinition = "INTEGER(19,0)")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
