package br.com.mtisi.dscatalog.resources;


import br.com.mtisi.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {



    @GetMapping
    public ResponseEntity<List<Category>> finall(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1L, "Books"));
        categoryList.add(new Category(2l,"Eletronics"));
        return ResponseEntity.ok().body(categoryList);
    }

}
