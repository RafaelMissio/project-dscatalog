package br.com.mtisi.dscatalog.repository;

import br.com.mtisi.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
