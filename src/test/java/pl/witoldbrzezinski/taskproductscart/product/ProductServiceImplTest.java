package pl.witoldbrzezinski.taskproductscart.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
  private static final Long SAMPLE_ID = 1L;
  public static final String SAMPLE_TITLE = "TEST TITLE";
  private static final Integer SAMPLE_QUANTITY = 2;

  private static final BigDecimal SAMPLE_PRICE = new BigDecimal(100);

  private ProductRepository productRepository;
  private ProductMapper productMapper;
  private ProductService productService;

  @BeforeEach
  void init() {
    productRepository = mock(ProductRepository.class);
    productMapper = new ProductMapper(new ModelMapper());
    productService = new ProductServiceImpl(productRepository, productMapper);
  }

  @Test
  void shouldAddNewProduct() {
    // given
    ProductDTORequest productDTORequest =
        ProductDTORequest.builder()
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    ProductEntity productEntity =
        ProductEntity.builder()
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    // when
    when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
    // then
    assertThat(productService.add(productDTORequest))
        .usingRecursiveComparison()
        .isEqualTo(productMapper.toDTO(productEntity));
  }

  @Test
  void shouldThrowExceptionWhenAddingAlreadyExistingProduct() {
    // given
    ProductDTORequest productDTORequest =
        ProductDTORequest.builder()
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    // when
    when(productRepository.existsByTitle(SAMPLE_TITLE)).thenReturn(true);
    // then
    assertThrowsExactly(
        ProductAlreadyExistsException.class, () -> productService.add(productDTORequest));
  }

  @Test
  void shouldRemoveProduct() {
    // given
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .isDeleted(false)
            .build();
    // when
    when(productRepository.findById(SAMPLE_ID)).thenReturn(Optional.of(productEntity));
    productService.remove(SAMPLE_ID);
    // then
    assertTrue(productEntity.isDeleted());
  }

  @Test
  void shouldUpdateProduct() {
    // given
    ProductDTORequest productDTORequest =
        ProductDTORequest.builder()
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    ProductEntity productEntity =
        ProductEntity.builder()
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    // when
    when(productRepository.findById(SAMPLE_ID)).thenReturn(Optional.of(productEntity));
    when(productRepository.save(productMapper.toEntity(productDTORequest)))
        .thenReturn(productEntity);
    // then
    assertThat(productService.update(SAMPLE_ID, productDTORequest))
        .usingRecursiveComparison()
        .isEqualTo(productMapper.toDTO(productEntity));
  }

  @Test
  void shouldGetListOfProducts() {
    // given
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(SAMPLE_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    List<ProductEntity> products = List.of(productEntity);
    ProductDTOResponse productDTOResponse =
        ProductDTOResponse.builder()
            .id(SAMPLE_ID)
            .title(SAMPLE_TITLE)
            .price(SAMPLE_PRICE)
            .quantity(SAMPLE_QUANTITY)
            .build();
    // when
    when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(products));
    // then
    assertThat(productDTOResponse)
        .usingRecursiveComparison()
        .isEqualTo(productService.getAll(Pageable.unpaged()).get(0));
  }
}
