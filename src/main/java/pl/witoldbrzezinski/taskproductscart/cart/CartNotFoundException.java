package pl.witoldbrzezinski.taskproductscart.cart;

public class CartNotFoundException extends RuntimeException {
  public CartNotFoundException(Long id) {
    super(String.format("Product with id %d not found", id));
  }
}
