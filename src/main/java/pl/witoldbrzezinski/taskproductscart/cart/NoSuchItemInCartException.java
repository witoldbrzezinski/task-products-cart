package pl.witoldbrzezinski.taskproductscart.cart;

public class NoSuchItemInCartException extends RuntimeException{
    public NoSuchItemInCartException(Long productId, Long cartId) {
      super(String.format("Product with id %d not found in cart with id %d", productId,cartId));
    }
}
