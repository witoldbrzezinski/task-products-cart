package pl.witoldbrzezinski.taskproductscart.product;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDTOResponse {
  private Long id;
  private String title;
  private BigDecimal price;

  @Min(0)
  private Integer quantity;

  private boolean isDeleted;
}
