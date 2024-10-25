package com.mapiz.mystore.sales.domain.repository;

import com.mapiz.mystore.sales.domain.SaleLine;
import java.util.List;

public interface SaleLineRepository {
  List<SaleLine> saveAll(List<SaleLine> model);
}
