package br.com.mtisi.dscatalog.services;

import br.com.mtisi.dscatalog.DTO.CategoryDTO;
import br.com.mtisi.dscatalog.entities.Category;
import br.com.mtisi.dscatalog.repository.CategoryRepository;
import br.com.mtisi.dscatalog.services.exceptions.DataBaseException;
import br.com.mtisi.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categoryLis = repository.findAll();

        return categoryLis.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category category = obj.orElseThrow(() -> new ResourceNotFoundException("entidade não encontrada"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id nao encontrado" + id);
        }

    }

    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found" + id);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrety violation");
        }


    }
}
