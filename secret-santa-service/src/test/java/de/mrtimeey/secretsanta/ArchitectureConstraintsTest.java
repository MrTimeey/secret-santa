package de.mrtimeey.secretsanta;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packagesOf = ArchitectureConstraintsTest.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureConstraintsTest {

    @ArchTest
    static final ArchRule no_package_cycles =
            slices()
                    .matching("de.mrtimeey.secretsanta.(*)..")
                    .should()
                    .beFreeOfCycles();


}
