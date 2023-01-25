package pl.witoldbrzezinski.taskproductscart.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;
    private boolean isDeleted;
    private final String uuid = UUID.randomUUID().toString();
    @Version
    private Long version;
    @OneToMany(mappedBy="cart",cascade = CascadeType.MERGE)
    private List<ProductEntity> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity that = (CartEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public void addProduct(ProductEntity product){
        product.setCart(this);
        products.add(product);
    }

    public void removeProduct(ProductEntity product){
        product.setCart(null);
        products.remove(product);
    }
}
