package pl.witoldbrzezinski.taskproductscart.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import pl.witoldbrzezinski.taskproductscart.IntegrationTestDB;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;
import pl.witoldbrzezinski.taskproductscart.product.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/clean-carts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartIntegrationTest extends IntegrationTestDB {

  private static final BigDecimal PRICE_ZER0 = new BigDecimal(0);
  private static final Long SAMPLE_PRODUCT_ID = 1L;
  private static final Long SAMPLE_CART_ID = 1L;
  public static final String SAMPLE_TITLE = "TEST TITLE";
  private static final Integer SAMPLE_QUANTITY = 2;
  private static final BigDecimal SAMPLE_PRICE = new BigDecimal(100);
  @Autowired private CartRepository cartRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @SneakyThrows
  void shouldCreateCart() {
    // given
    CartEntity cartEntity = CartEntity.builder().totalPrice(PRICE_ZER0).products(List.of()).build();
    cartRepository.save(cartEntity);
    // when then
    mockMvc
        .perform(
            post("/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartEntity)))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  @SneakyThrows
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
    productRepository.save(productEntity);
    cartEntity.addProduct(productEntity);
    cartRepository.save(cartEntity);
    // when then
    mockMvc
        .perform(
            put("/carts/1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartEntity)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @SneakyThrows
  @Disabled
  void shouldRemoveProductFromCart() {
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
    productRepository.save(productEntity);
    cartEntity.addProduct(productEntity);
    cartRepository.save(cartEntity);
    // when then
    mockMvc
        .perform(
            delete("/carts/1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartEntity)))
        .andExpect(status().isNoContent())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldGetProductsFromCart() {
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
    productRepository.save(productEntity);
    cartEntity.addProduct(productEntity);
    cartRepository.save(cartEntity);
    // when then
    mockMvc
        .perform(
            get("/carts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartEntity)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldCalculateTotalPrice() {
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
    productRepository.save(productEntity);
    cartEntity.addProduct(productEntity);
    cartRepository.save(cartEntity);
    // when then
    mockMvc
        .perform(
            get("/carts/1/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartEntity)))
        .andExpect(status().isOk())
        .andReturn();
  }
}
