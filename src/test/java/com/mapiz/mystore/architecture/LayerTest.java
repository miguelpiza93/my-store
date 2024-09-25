package com.mapiz.mystore.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayerTest {

    private static final String ROOT_PACKAGE = "com.mapiz.mystore";
    private static final String DOMAIN = "Domain";
    private static final String APPLICATION = "Application";
    private static final String INFRASTRUCTURE = "Infrastructure";
    private JavaClasses javaClasses;

    @BeforeEach
    void setUp() {
        this.javaClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(ROOT_PACKAGE);
    }

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
