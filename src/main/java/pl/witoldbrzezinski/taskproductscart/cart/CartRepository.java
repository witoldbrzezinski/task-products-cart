package pl.witoldbrzezinski.taskproductscart.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;

import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
}
