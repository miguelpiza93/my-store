package com.mapiz.mystore.sales.application.command;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterSaleCommand {
  List<RegisterSaleCommandItem> items;
}
