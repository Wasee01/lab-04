package com.layering.lab_04;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class LayeredArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void loadClasses() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.layering.lab_04");
    }

    // RULE 1: Layers may only depend in one direction:
    // controller -> service -> repository
    @Test
    void rule1_layersOnlyDependDownwards() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

        rule.check(classes);
    }

    // RULE 2: Service must not depend on Spring MVC / servlet / http types.
    // (No HTTP concern in the service layer.)
    @Test
    void rule2_serviceHasNoHttpConcerns() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "org.springframework.web..",
                        "jakarta.servlet..",
                        "org.springframework.http..");

        rule.check(classes);
    }

    // RULE 3: Service must not depend on SQL / JDBC / JPA / Hibernate.
    // (No SQL concern in the service layer.)
    @Test
    void rule3_serviceHasNoSqlConcerns() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "java.sql..",
                        "javax.sql..",
                        "jakarta.persistence..",
                        "org.springframework.jdbc..",
                        "org.hibernate..");

        rule.check(classes);
    }

    // RULE 4: Controllers must never touch the repository layer.
    @Test
    void rule4_controllersDoNotTouchRepositories() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..repository..");

        rule.check(classes);
    }

    // RULE 5: No controller class may reference a model type.
    // (This is the structural proof that DTOs are used everywhere.)
    @Test
    void rule5_nomodelTypesInControllers() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..model..");

        rule.check(classes);
    }
}