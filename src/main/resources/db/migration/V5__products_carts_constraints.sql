ALTER TABLE products_carts ADD CONSTRAINT "fk_product" FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE products_carts ADD CONSTRAINT "fk_cart" FOREIGN KEY (cart_id) REFERENCES carts (id);
