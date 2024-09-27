package com.mapiz.mystore.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class AnnotationTest extends BaseArchitectureTest {

  @Test
  void testUseCaseImplClassesAreAnnotatedWithComponentOrService() {
    classes()
        .that()
        .resideInAPackage("..usecase.impl..")
        .should()
        .beAnnotatedWith(Service.class)
        .orShould()
        .beAnnotatedWith(Component.class)
        .check(this.javaClasses);
  }

  @Test
  void testUseCaseClassesAreAnnotatedWithFunctionalInterface() {
    classes()
        .that()
        .resideInAPackage("..usecase..")
        .and()
        .haveSimpleNameEndingWith("UseCase")
        .should()
        .beInterfaces()
        .andShould()
        .beAnnotatedWith(FunctionalInterface.class)
        .check(this.javaClasses);
  }
}
