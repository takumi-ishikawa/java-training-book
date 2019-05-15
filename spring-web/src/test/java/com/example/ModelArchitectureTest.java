package com.example;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(packages = "com.example.model")
class ModelArchitectureTest {

  @Test
  void modelDidNotDependOnAnyPackages() {
    final JavaClasses classes = new ClassFileImporter().importPackages("com.example.model");

    final ArchRule rule = classes()
        .should().accessClassesThat().resideInAnyPackage("java.lang", "java.util", "java.util.function", "java.time", "com.example.model")
        .because("model should be independent of any components");

    rule.check(classes);
  }
}
