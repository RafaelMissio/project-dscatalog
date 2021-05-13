package br.com.mtisi.dscatalog.resources;


import br.com.mtisi.dscatalog.entities.Category;
import br.com.mtisi.dscatalog.repository.CategoryRepository;
import br.com.mtisi.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> finall() {
        List<Category> categoryList = service.findAll();
        return ResponseEntity.ok().body(categoryList);
    }


}
