package com.mapiz.mystore.unit.infrastructure.persistence.repository;

import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUnitRepository extends JpaRepository<UnitEntity, Integer> {}
