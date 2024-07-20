package br.com.niles.dscatalog.dto;

import br.com.niles.dscatalog.entities.Category;

import java.util.Optional;

public class CategoryDto {
    private Long id;
    private String name;

    public CategoryDto(Optional<Category> category) {
    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }


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
