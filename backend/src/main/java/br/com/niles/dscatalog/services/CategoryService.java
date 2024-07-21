package br.com.niles.dscatalog.services;

import br.com.niles.dscatalog.dto.CategoryDto;
import br.com.niles.dscatalog.entities.Category;
import br.com.niles.dscatalog.repositories.CategoryRepository;
import br.com.niles.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        List<Category> categories = repository.findAll();
        List<CategoryDto> categoriesDto = categories.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
        return categoriesDto;
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!!!"));
        CategoryDto categoryDto = new CategoryDto(category);
        return categoryDto;
    }

    @Transactional()
    public CategoryDto insert(CategoryDto categoryDto) {
        Category category = repository.save(new Category(categoryDto));
        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        try {
            Category category = repository.getReferenceById(id);
            category.setName(categoryDto.getName());
            category = repository.save(category);
            return new CategoryDto(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Falha de integridade referencial.");
        }
    }
}