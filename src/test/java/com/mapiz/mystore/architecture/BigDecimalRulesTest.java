package com.mapiz.mystore.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class BigDecimalRulesTest extends BaseArchitectureTest {

  @Test
  public void noClassShouldInvokeBigDecimalMethodsExceptBigDecimalUtils() {
    ArchRule rule =
        classes()
            .that()
            .doNotHaveSimpleName("BigDecimalUtils")
            .should(notInvokeBigDecimalMethods())
            .because("BigDecimal operations must be done through BigDecimalUtils");

    rule.check(this.javaClasses); // Esto debería causar la falla si hay violaciones
  }

  private ArchCondition<JavaClass> notInvokeBigDecimalMethods() {
    return new ArchCondition<>("not invoke BigDecimal methods or constructors") {
      @Override
      public void check(JavaClass clazz, ConditionEvents events) {
        for (JavaMethod method : clazz.getMethods()) {
          method.getMethodCallsFromSelf().stream()
              .filter(call -> call.getTargetOwner().isAssignableTo(BigDecimal.class))
              .forEach(
                  call -> {
                    String message =
                        String.format(
                            "Class %s in method %s calls BigDecimal method %s",
                            clazz.getName(), method.getFullName(), call.getTarget().getFullName());
                    events.add(SimpleConditionEvent.violated(method, message));
                  });

          method.getConstructorCallsFromSelf().stream()
              .filter(call -> call.getTargetOwner().isAssignableTo(BigDecimal.class))
              .forEach(
                  call -> {
                    String message =
                        String.format(
                            "Class %s in method %s calls BigDecimal constructor %s",
                            clazz.getName(), method.getFullName(), call.getTarget().getFullName());
                    events.add(SimpleConditionEvent.violated(method, message));
                  });
        }
      }
    };
  }
}
