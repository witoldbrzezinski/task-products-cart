package pl.witoldbrzezinski.taskproductscart.cart;

import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;

import java.util.List;

public interface CartService {

    CartDTOResponse create();

    CartDTOResponse addProduct(Long cartId, Long productId);

    CartDTOResponse removeProduct(Long cartId, Long productId);

    List<ProductEntity> getAllProductsFromCart(Long cartId);

}
