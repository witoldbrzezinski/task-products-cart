package pl.witoldbrzezinski.taskproductscart.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  public ProductDTOResponse add(ProductDTORequest productDTORequest) {
    if (productRepository.existsByTitle(productDTORequest.getTitle())){
      throw new ProductAlreadyExistsException(productDTORequest.getTitle());
    }
    ProductEntity productEntity = productMapper.toEntity(productDTORequest);
    productRepository.save(productEntity);
    return productMapper.toDTO(productEntity);
  }

  @Override
  public void remove(Long id) {}

  @Override
  public ProductDTOResponse update(Long id, ProductDTORequest productDTORequest) {
    return null;
  }

  @Override
  public List<ProductDTOResponse> getAll(Pageable pageable) {
    return productRepository.findAll(pageable).stream()
        .map(productMapper::toDTO)
        .collect(Collectors.toList());
  }

  private ProductEntity findProduct(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }
}
