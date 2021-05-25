package br.com.mtisi.dscatalog.services;


import br.com.mtisi.dscatalog.DTO.CategoryDTO;
import br.com.mtisi.dscatalog.DTO.ProductDTO;
import br.com.mtisi.dscatalog.entities.Category;
import br.com.mtisi.dscatalog.entities.Product;
import br.com.mtisi.dscatalog.repository.CategoryRepository;
import br.com.mtisi.dscatalog.repository.ProductRepository;
import br.com.mtisi.dscatalog.services.exceptions.DataBaseException;
import br.com.mtisi.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPage(PageRequest pageRequest) {
        Page<Product> listProduct = repository.findAll(pageRequest);
        return listProduct.map(p -> new ProductDTO(p));

    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product product = obj.orElseThrow(() -> new ResourceNotFoundException("ENTITY NOT FOUND"));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO Insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID NOT FOUND " + e);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ENTITY NOT FOUD " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("INTEGRETY VIOLATION " + e);
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category category = categoryRepository.getOne(catDTO.getId());
            entity.getCategories().add(category);
        }
    }
}
