package pl.witoldbrzezinski.taskproductscart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.witoldbrzezinski.taskproductscart.product.ProductDTOResponse;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CartDTOResponse create() {
    return cartService.create();
  }

  @PutMapping("/{cartId}/products/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public CartDTOResponse addProduct(@PathVariable Long cartId, @PathVariable Long productId) {
    return cartService.addProduct(cartId, productId);
  }

  @DeleteMapping("/{cartId}/products/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public CartDTOResponse removeProduct(@PathVariable Long cartId, @PathVariable Long productId) {
    return cartService.removeProduct(cartId, productId);
  }

  @GetMapping("/{cartId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDTOResponse> getProductsFromCart(@PathVariable Long cartId) {
    return cartService.getAllProductsFromCart(cartId);
  }

  @GetMapping("/{cartId}/calculate")
  @ResponseStatus(HttpStatus.OK)
  public BigDecimal getCartTotalValue(@PathVariable Long cartId) {
    return cartService.getCartTotalValue(cartId);
  }
}
