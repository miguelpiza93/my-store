package com.mapiz.mystore.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;

public class ModuleTest extends BaseArchitectureTest {

  @Test
  void testCommandsMustBePlacedInApplication() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Command")
        .should()
        .resideInAnyPackage("..application.command..")
        .check(this.javaClasses);
  }

  @Test
  void testRepositoryImplMustImplementRepositoryFromDomain() {
    classes()
        .that()
        .haveSimpleNameEndingWith("RepositoryImpl")
        .should()
        .implement(JavaClass.Predicates.resideInAPackage("..domain.repository.."))
        .andShould()
        .resideInAnyPackage("..infrastructure.persistence.repository.impl..")
        .check(this.javaClasses);
  }
}
