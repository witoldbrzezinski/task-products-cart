package pl.witoldbrzezinski.taskproductscart.product;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDTOResponse add(ProductDTORequest productDTORequest);
    void remove(Long id);
    ProductDTOResponse update(Long id, ProductDTORequest productDTORequest);
    List<ProductDTOResponse> getAll(Pageable pageable);
}
