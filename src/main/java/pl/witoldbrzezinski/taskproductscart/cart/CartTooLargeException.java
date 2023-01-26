package pl.witoldbrzezinski.taskproductscart.cart;

public class CartTooLargeException extends RuntimeException {

  public CartTooLargeException() {
    super("You can't add more than 3 items to a cart");
  }
}
