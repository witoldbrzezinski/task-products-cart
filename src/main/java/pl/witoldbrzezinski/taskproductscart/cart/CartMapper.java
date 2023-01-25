package pl.witoldbrzezinski.taskproductscart.cart;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.witoldbrzezinski.taskproductscart.product.ProductDTORequest;
import pl.witoldbrzezinski.taskproductscart.product.ProductDTOResponse;
import pl.witoldbrzezinski.taskproductscart.product.ProductEntity;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final ModelMapper modelMapper;

    CartDTOResponse toDTO(CartEntity cartEntity) {
        return modelMapper.map(cartEntity, CartDTOResponse.class);
    }

    CartEntity toEntity(CartDTORequest cartDTORequest) {
        return modelMapper.map(cartDTORequest, CartEntity.class);
    }
}
