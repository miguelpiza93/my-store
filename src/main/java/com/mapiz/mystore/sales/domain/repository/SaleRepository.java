package com.mapiz.mystore.sales.domain.repository;

import com.mapiz.mystore.sales.domain.Sale;
import java.util.List;

public interface SaleRepository {
  List<Sale> saveAll(List<Sale> model);
}
