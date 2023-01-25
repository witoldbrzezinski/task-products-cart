package pl.witoldbrzezinski.taskproductscart.cart;

import pl.witoldbrzezinski.taskproductscart.product.ProductDTOResponse;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    CartDTOResponse create();

    CartDTOResponse addProduct(Long cartId, Long productId);

    CartDTOResponse removeProduct(Long cartId, Long productId);

    List<ProductDTOResponse> getAllProductsFromCart(Long cartId);

   BigDecimal getCartTotalValue(Long cartId);

}
