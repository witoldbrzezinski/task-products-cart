package pl.witoldbrzezinski.taskproductscart.product;

import java.util.List;

public interface ProductService {

    ProductDTOResponse add(ProductDTORequest productDTORequest);
    void remove(Long id);
    ProductDTOResponse update(Long id, ProductDTORequest productDTORequest);
    List<ProductDTOResponse> getAll();
}
