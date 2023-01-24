package pl.witoldbrzezinski.taskproductscart.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

  private final ModelMapper modelMapper;

  ProductDTOResponse toDTO(ProductEntity productEntity) {
    return modelMapper.map(productEntity, ProductDTOResponse.class);
  }

  ProductEntity toEntity(ProductDTORequest productDTORequest) {
    return modelMapper.map(productDTORequest, ProductEntity.class);
  }
}
