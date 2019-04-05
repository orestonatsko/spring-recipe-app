package com.springframework.domain;

import javax.persistence.*;

/**
 * Created by jt on 6/13/17.
 */
@Entity
@Table(name = "note")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", columnDefinition = "INTEGER(19,0)")
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "recipe_id", columnDefinition = "INTEGER(19,0)")
//    private Recipe recipe;

    @Column(name = "recipe_note", columnDefinition = "TEXT(1000)")
    private String recipeNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Recipe getRecipe() {
//        return recipe;
//    }
//
//    public void setRecipe(Recipe recipe) {
//        this.recipe = recipe;
//    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
