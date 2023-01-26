package pl.witoldbrzezinski.taskproductscart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.witoldbrzezinski.taskproductscart.product.ProductDTOResponse;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;
import pl.witoldbrzezinski.taskproductscart.product.ProductMapper;
import pl.witoldbrzezinski.taskproductscart.product.ProductNotFoundException;
import pl.witoldbrzezinski.taskproductscart.product.ProductRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private static final int MAX_CART_SIZE = 3;
  private final CartRepository cartRepository;
  private final CartMapper cartMapper;
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;

  @Override
  public CartDTOResponse create() {
    CartEntity cartEntity = new CartEntity();
    cartEntity.setTotalPrice(new BigDecimal(0));
    cartRepository.save(cartEntity);
    return cartMapper.toDTO(cartEntity);
  }

  @Override
  @Transactional
  public CartDTOResponse addProduct(Long cartId, Long productId) {
    ProductEntity productEntity = findProduct(productId);
    CartEntity cartEntity = findCart(cartId);
    List<ProductEntity> products = cartEntity.getProducts();
    if (products.size() >= MAX_CART_SIZE) {
      throw new CartTooLargeException();
    } else {
      cartEntity.addProduct(productEntity);
      cartEntity.setTotalPrice(cartEntity.getTotalPrice().add(productEntity.getPrice()));
      productEntity.setQuantity(productEntity.getQuantity() - 1);
      cartRepository.save(cartEntity);
    }
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
      cartEntity.setTotalPrice(cartEntity.getTotalPrice().subtract(productEntity.getPrice()));
      productEntity.setQuantity(productEntity.getQuantity() + 1);
      cartRepository.save(cartEntity);
    }
    return cartMapper.toDTO(cartEntity);
  }

  @Override
  public List<ProductDTOResponse> getAllProductsFromCart(Long cartId) {
    CartEntity cartEntity = findCart(cartId);
    return cartEntity.getProducts().stream().map(productMapper::toDTO).collect(Collectors.toList());
  }

  @Override
  public BigDecimal getCartTotalValue(Long cartId) {
    CartEntity cartEntity = findCart(cartId);
    return cartEntity.getTotalPrice();
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
