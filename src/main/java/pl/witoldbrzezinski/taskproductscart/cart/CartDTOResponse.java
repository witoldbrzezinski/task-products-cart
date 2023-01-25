package pl.witoldbrzezinski.taskproductscart.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartDTOResponse {

    private Long id;
    private BigDecimal totalPrice;
    private boolean isDeleted;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<ProductEntity> products = new ArrayList<>();

}
