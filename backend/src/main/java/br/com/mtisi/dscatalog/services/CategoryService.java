package br.com.mtisi.dscatalog.services;

import br.com.mtisi.dscatalog.DTO.CategoryDTO;
import br.com.mtisi.dscatalog.entities.Category;
import br.com.mtisi.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryDTO> findAll(){
        List<Category> categoryLis =repository.findAll();

       return categoryLis.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

    }

}
