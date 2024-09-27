package com.mapiz.mystore.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import org.junit.jupiter.api.Test;

public class LayerTest extends BaseArchitectureTest {

  private static final String DOMAIN = "Domain";
  private static final String APPLICATION = "Application";
  private static final String INFRASTRUCTURE = "Infrastructure";

  @Test
  void testLayerDependencies() {
    layeredArchitecture()
        .consideringAllDependencies()
        .layer(DOMAIN)
        .definedBy("..domain..")
        .layer(APPLICATION)
        .definedBy("..application..")
        .layer(INFRASTRUCTURE)
        .definedBy("..infrastructure..")
        .whereLayer(INFRASTRUCTURE)
        .mayNotBeAccessedByAnyLayer()
        .whereLayer(APPLICATION)
        .mayOnlyBeAccessedByLayers(INFRASTRUCTURE)
        .whereLayer(DOMAIN)
        .mayOnlyBeAccessedByLayers(APPLICATION, INFRASTRUCTURE)
        .check(this.javaClasses);
  }
}
