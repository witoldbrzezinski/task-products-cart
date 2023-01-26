package pl.witoldbrzezinski.taskproductscart.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;
import pl.witoldbrzezinski.taskproductscart.product.ProductMapper;
import pl.witoldbrzezinski.taskproductscart.product.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartServiceImplTest {

  private static final BigDecimal PRICE_ZER0 = new BigDecimal(0);

  private static final Long SAMPLE_PRODUCT_ID = 1L;
  private static final Long SAMPLE_CART_ID = 1L;
  public static final String SAMPLE_TITLE = "TEST TITLE";
  private static final Integer SAMPLE_QUANTITY = 2;

  private static final BigDecimal SAMPLE_PRICE = new BigDecimal(100);
  private static final int MAX_CART_SIZE = 3;

  private CartRepository cartRepository;
  private CartMapper cartMapper;
  private ProductRepository productRepository;
  private ProductMapper productMapper;
  private CartService cartService;

  @BeforeEach
  void init() {
    cartRepository = mock(CartRepository.class);
    cartMapper = new CartMapper(new ModelMapper());
    productRepository = mock(ProductRepository.class);
    productMapper = new ProductMapper(new ModelMapper());
    cartService = new CartServiceImpl(cartRepository, cartMapper, productMapper, productRepository);
  }

  @Test
  void shouldCreateCart() {
    // given
    CartEntity cartEntity = CartEntity.builder().totalPrice(PRICE_ZER0).products(List.of()).build();
    // when
    when(cartRepository.save(any(CartEntity.class))).thenReturn(cartEntity);
    // then
    assertThat(cartService.create())
        .usingRecursiveComparison()
        .isEqualTo(cartMapper.toDTO(cartEntity));
  }

  @Test
  void shouldAddProductToCart() {
    // given
    CartEntity cartEntity =
        CartEntity.builder()
            .id(SAMPLE_CART_ID)
            .totalPrice(PRICE_ZER0)
            .products(new ArrayList<>())
            .build();
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_PRODUCT_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .carts(new ArrayList<>())
            .build();
    cartEntity.addProduct(productEntity);
    // when
    when(productRepository.findById(SAMPLE_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
    when(cartRepository.findById(SAMPLE_CART_ID)).thenReturn(Optional.of(cartEntity));
    // then
    assertThat(cartService.addProduct(SAMPLE_CART_ID, SAMPLE_PRODUCT_ID))
        .usingRecursiveComparison()
        .isEqualTo(cartMapper.toDTO(cartEntity));
  }

  @Test
  void shouldNotAddProductToCartWhenSizeIsExceeded() {
    // given
    CartEntity cartEntity =
        CartEntity.builder()
            .id(SAMPLE_CART_ID)
            .totalPrice(PRICE_ZER0)
            .products(new ArrayList<>())
            .build();
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_PRODUCT_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .carts(new ArrayList<>())
            .build();
    for (int i = 0; i < MAX_CART_SIZE; i++) {
      cartEntity.addProduct(productEntity);
    }
    // when
    when(productRepository.findById(SAMPLE_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
    when(cartRepository.findById(SAMPLE_CART_ID)).thenReturn(Optional.of(cartEntity));
    // then
    assertThrowsExactly(
        CartTooLargeException.class,
        () -> cartService.addProduct(SAMPLE_CART_ID, SAMPLE_PRODUCT_ID));
  }

  @Test
  void shouldRemoveProductToCart() {
    // given
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_PRODUCT_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .carts(new ArrayList<>())
            .build();
    CartEntity cartEntity =
        CartEntity.builder()
            .id(SAMPLE_CART_ID)
            .totalPrice(PRICE_ZER0)
            .products(new ArrayList<>())
            .build();
    cartEntity.addProduct(productEntity);
    // when
    when(productRepository.findById(SAMPLE_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
    when(cartRepository.findById(SAMPLE_CART_ID)).thenReturn(Optional.of(cartEntity));
    // then
    assertThat(cartService.removeProduct(SAMPLE_CART_ID, SAMPLE_PRODUCT_ID))
        .usingRecursiveComparison()
        .isEqualTo(cartMapper.toDTO(cartEntity));
  }

  @Test
  void shouldNotRemoveProductThatIsNotInACart() {
    // given
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_PRODUCT_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .carts(new ArrayList<>())
            .build();
    CartEntity cartEntity =
        CartEntity.builder()
            .id(SAMPLE_CART_ID)
            .totalPrice(PRICE_ZER0)
            .products(new ArrayList<>())
            .build();
    // when
    when(productRepository.findById(SAMPLE_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
    when(cartRepository.findById(SAMPLE_CART_ID)).thenReturn(Optional.of(cartEntity));
    // then
    assertThrowsExactly(
        NoSuchItemInCartException.class,
        () -> cartService.removeProduct(SAMPLE_CART_ID, SAMPLE_PRODUCT_ID));
  }

  @Test
  void shouldReturnListOfProductsFromCart() {
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_PRODUCT_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .carts(new ArrayList<>())
            .build();
    CartEntity cartEntity =
        CartEntity.builder()
            .id(SAMPLE_CART_ID)
            .totalPrice(PRICE_ZER0)
            .products(new ArrayList<>())
            .build();
    cartEntity.addProduct(productEntity);
    // when
    when(productRepository.findById(SAMPLE_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
    when(cartRepository.findById(SAMPLE_CART_ID)).thenReturn(Optional.of(cartEntity));
    // then
    assertThat(cartService.getAllProductsFromCart(SAMPLE_CART_ID).get(0))
        .usingRecursiveComparison()
        .isEqualTo(productMapper.toDTO(productEntity));
  }

  @Test
  void shouldReturnCartTotalPrice() {
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_PRODUCT_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .carts(new ArrayList<>())
            .build();
    CartEntity cartEntity =
        CartEntity.builder()
            .id(SAMPLE_CART_ID)
            .totalPrice(PRICE_ZER0)
            .products(new ArrayList<>())
            .build();
    cartEntity.addProduct(productEntity);
    // when
    when(productRepository.findById(SAMPLE_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
    when(cartRepository.findById(SAMPLE_CART_ID)).thenReturn(Optional.of(cartEntity));
    when(cartService.addProduct(SAMPLE_CART_ID, SAMPLE_PRODUCT_ID))
        .thenReturn(cartMapper.toDTO(cartEntity));
    // then
    assertEquals(cartService.getCartTotalValue(SAMPLE_CART_ID), SAMPLE_PRICE);
  }
}
