package com.mapiz.mystore.product.application.dto;

import java.math.BigDecimal;

public record ProductPriceDetail(Integer unitId, String unitName, BigDecimal price) {}
