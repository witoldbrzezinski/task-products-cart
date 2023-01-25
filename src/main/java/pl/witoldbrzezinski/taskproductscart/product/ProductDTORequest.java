package pl.witoldbrzezinski.taskproductscart.product;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProductDTORequest {

  @NotNull private String title;
  @NotNull private BigDecimal price;

  @NotNull
  @Min(value = 0)
  private Integer quantity;
}
