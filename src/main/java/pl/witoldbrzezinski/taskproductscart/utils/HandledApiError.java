package pl.witoldbrzezinski.taskproductscart.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HandledApiError {

  private LocalDateTime localDateTime;
  private String message;
}
