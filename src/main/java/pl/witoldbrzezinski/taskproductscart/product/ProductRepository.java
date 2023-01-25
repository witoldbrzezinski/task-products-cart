package pl.witoldbrzezinski.taskproductscart.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByTitle(String title);
}