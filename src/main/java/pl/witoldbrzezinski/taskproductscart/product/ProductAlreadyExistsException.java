package pl.witoldbrzezinski.taskproductscart.product;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String title) {
      super(String.format("Product with title %s already exists. Please update the quantity of product", title));
    }
}
