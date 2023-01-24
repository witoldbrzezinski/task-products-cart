package pl.witoldbrzezinski.taskproductscart.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductDTOResponse add(ProductDTORequest productDTORequest) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public ProductDTOResponse update(Long id, ProductDTORequest productDTORequest) {
        return null;
    }

    @Override
    public List<ProductDTOResponse> getAll() {
        return null;
    }
}
