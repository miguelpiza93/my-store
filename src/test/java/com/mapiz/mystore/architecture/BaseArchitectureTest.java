package com.mapiz.mystore.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;

public class BaseArchitectureTest {

    private static final String ROOT_PACKAGE = "com.mapiz.mystore";

    protected JavaClasses javaClasses;

    @BeforeEach
    void setUp() {
        this.javaClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(ROOT_PACKAGE);
    }
}
