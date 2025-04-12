package com.mapiz.mystore.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;

public class PackagesTest extends BaseArchitectureTest {

  @Test
  void testInterfacesMustNotBePlacedInImplementationPackages() {
    noClasses().that().resideInAPackage("..impl..").should().beInterfaces().check(this.javaClasses);
  }

  @Test
  void testUseCasePackageMustContainUseCaseOrUseCaseImpl() {
    classes()
        .that()
        .resideInAPackage("..usecase..")
        .should()
        .haveSimpleNameEndingWith("UseCase")
        .orShould()
        .haveSimpleNameEndingWith("UseCaseImpl")
        .check(this.javaClasses);
  }

  @Test
  void testBaseUseCasePackageMustContainBaseUseCase() {
    classes()
        .that()
        .resideInAPackage("..usecase.base..")
        .should()
        .haveSimpleNameEndingWith("UseCase")
        .andShould()
        .haveSimpleNameStartingWith("Base")
        .check(this.javaClasses);
  }

  @Test
  void testInfrastructureRepositoryPackageMustContainJpaRepositoryOrRepositoryImpl() {
    classes()
        .that()
        .resideInAPackage("..persistence.repository..")
        .should()
        .haveSimpleNameStartingWith("Jpa")
        .orShould()
        .haveSimpleNameEndingWith("RepositoryImpl")
        .check(this.javaClasses);
  }
}
