package pl.witoldbrzezinski.taskproductscart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;
import pl.witoldbrzezinski.taskproductscart.product.ProductNotFoundException;
import pl.witoldbrzezinski.taskproductscart.product.ProductRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final CartMapper cartMapper;

  private final ProductRepository productRepository;

  @Override
  public CartDTOResponse create() {
    CartEntity cartEntity = new CartEntity();
    cartEntity.setValue(new BigDecimal(0));
    cartRepository.save(cartEntity);
    return cartMapper.toDTO(cartEntity);
  }

  @Override
  @Transactional
  public CartDTOResponse addProduct(Long cartId, Long productId) {
    ProductEntity productEntity = findProduct(productId);
    CartEntity cartEntity = findCart(cartId);
    cartEntity.addProduct(productEntity);
    cartEntity.setValue(cartEntity.getValue().add(productEntity.getPrice()));
    productEntity.setQuantity(productEntity.getQuantity() - 1);
    cartRepository.save(cartEntity);
    return cartMapper.toDTO(cartEntity);
  }

  @Override
  @Transactional
  public CartDTOResponse removeProduct(Long cartId, Long productId) {
    ProductEntity productEntity = findProduct(productId);
    CartEntity cartEntity = findCart(cartId);
    List<ProductEntity> products = cartEntity.getProducts();
    if (!products.contains(productEntity)) {
      throw new NoSuchItemInCartException(productId, cartId);
    } else {
      cartEntity.removeProduct(productEntity);
      cartEntity.setValue(cartEntity.getValue().subtract(productEntity.getPrice()));
      productEntity.setQuantity(productEntity.getQuantity() + 1);
      cartRepository.save(cartEntity);
    }
    return cartMapper.toDTO(cartEntity);
  }

  @Override
  public List<ProductEntity> getAllProductsFromCart(Long cartId) {
    CartEntity cartEntity = findCart(cartId);
    return cartEntity.getProducts();
  }

  private ProductEntity findProduct(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }

  private CartEntity findCart(Long cartId) {
    return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
  }
}
