package com.mapiz.mystore.shared;

import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface BigDecimalMapper {
  BigDecimalMapper INSTANCE = Mappers.getMapper(BigDecimalMapper.class);

  default BigDecimal map(BigDecimal value) {
    return BigDecimalUtils.valueOf(value);
  }
}
