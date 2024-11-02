package com.mapiz.mystore.sales.domain.repository;

import com.mapiz.mystore.sales.domain.Sale;
import java.util.List;

public interface SaleRepository {
  Sale save(Sale model);

  List<Sale> findAll();
}
