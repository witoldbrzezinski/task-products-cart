package pl.witoldbrzezinski.taskproductscart.cart;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {

  private final ModelMapper modelMapper;

  CartDTOResponse toDTO(CartEntity cartEntity) {
    return modelMapper.map(cartEntity, CartDTOResponse.class);
  }
}
