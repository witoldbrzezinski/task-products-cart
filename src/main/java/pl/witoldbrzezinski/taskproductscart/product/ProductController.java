package pl.witoldbrzezinski.taskproductscart.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private static final int MAX_PRODUCTS_PER_PAGE = 3;
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResponse add(@RequestBody ProductDTORequest productDTORequest){
        return productService.add(productDTORequest);
    }

    @GetMapping
    public List<ProductDTOResponse> getAll(@PageableDefault(size = MAX_PRODUCTS_PER_PAGE) Pageable pageable){
       return productService.getAll(pageable);
    }


}
