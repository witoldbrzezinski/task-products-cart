package pl.witoldbrzezinski.taskproductscart.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.witoldbrzezinski.taskproductscart.IntegrationTestDB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/clean-products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductIntegrationTest extends IntegrationTestDB {

  private static final Long SAMPLE_ID = 1L;
  public static final String SAMPLE_TITLE = "TEST TITLE";
  private static final Integer SAMPLE_QUANTITY = 2;

  private static final BigDecimal SAMPLE_PRICE = new BigDecimal(100);
  @Autowired private ProductRepository productRepository;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @SneakyThrows
  void shouldAddProduct() {
    // given
    ProductDTORequest productDTORequest =
        ProductDTORequest.builder()
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
      // when//then
      mockMvc
              .perform(
                      post("/products")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(productDTORequest)))
              .andExpect(status().isCreated())
              .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldRemoveProduct() {
    // given
    ProductEntity productEntity =
            ProductEntity.builder()
                    .id(SAMPLE_ID)
                    .title(SAMPLE_TITLE)
                    .price(SAMPLE_PRICE)
                    .quantity(SAMPLE_QUANTITY)
                    .build();
    productRepository.save(productEntity);
    // when then
    mockMvc.perform(delete("/products/1")).andExpect(status().isNoContent());
  }

  @Test
  @SneakyThrows
  void shouldUpdateProduct(){
    //given
    ProductEntity productEntity =
            ProductEntity.builder()
                    .id(SAMPLE_ID)
                    .title(SAMPLE_TITLE)
                    .price(SAMPLE_PRICE)
                    .quantity(SAMPLE_QUANTITY)
                    .build();
    productRepository.save(productEntity);
    ProductDTORequest productDTORequest =
            ProductDTORequest.builder()
                    .title(SAMPLE_TITLE)
                    .price(SAMPLE_PRICE)
                    .quantity(SAMPLE_QUANTITY)
                    .build();
    mockMvc
            .perform(
                    put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productDTORequest)))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldGetAllProducts(){
    //given
    ProductDTORequest productDTORequest =
            ProductDTORequest.builder()
                    .title(SAMPLE_TITLE)
                    .price(SAMPLE_PRICE)
                    .quantity(SAMPLE_QUANTITY)
                    .build();
    ProductEntity productEntity =
            ProductEntity.builder()
                    .id(SAMPLE_ID)
                    .title(SAMPLE_TITLE)
                    .price(SAMPLE_PRICE)
                    .quantity(SAMPLE_QUANTITY)
                    .build();
    ProductDTOResponse productDTOResponse =
            ProductDTOResponse.builder()
                    .id(SAMPLE_ID)
                    .title(SAMPLE_TITLE)
                    .price(SAMPLE_PRICE)
                    .quantity(SAMPLE_QUANTITY)
                    .build();
    productRepository.save(productEntity);
    // when
    MvcResult result =
            mockMvc
                    .perform(
                            get("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(productDTORequest)))
                    .andExpect(status().isOk())
                    .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
            .isEqualTo("[" + objectMapper.writeValueAsString(productDTOResponse) + "]");
}
}
