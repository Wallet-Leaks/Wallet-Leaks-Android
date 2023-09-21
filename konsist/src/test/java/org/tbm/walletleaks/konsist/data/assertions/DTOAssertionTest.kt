package org.tbm.walletleaks.konsist.data.assertions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.junit.Test
import org.tbm.walletleaks.konsist.base.ClassAssertionTest

internal class DTOAssertionTest : ClassAssertionTest() {
    override val packageClassesResideIn = "..data..dtos.."

    override val classSuffix = "DTO"

    @Test
    internal fun `ensure that all classes with 'DTO' suffix reside in 'dtos' package`() {
        assertClassesPackage()
    }

    @Test
    internal fun `ensure that all classes that reside in 'dtos' package have 'DTO' suffix`() {
        assertClassesThatResideInPackage()
    }

    @Test
    internal fun `ensure that all classes with 'DTO' suffix are annotated with 'Serializable' and have all properties annotated with 'SerialName' and those properties are public and constant`() {
        filterByAnnotationAndAssertClassesPropertiesThatResideInPackage(Serializable::class) {
            hasPublicOrDefaultModifier && hasValModifier && hasAnnotationsOf(SerialName::class)
        }
    }

    @Test
    internal fun `ensure that all classes that reside in 'dtos' and inherit from 'DataMapper' are 'data class'`() {
        filterAndAssertClassesThatResideInPackage(filter = { hasParents("DataMapper") }) { hasDataModifier }
    }

    @Test
    internal fun `ensure that all classes that have 'toDomain()' function return the corresponding domain model`() {
        filterAndAssertClassesFunctionsThatResideInPackage(filter = { hasParents("DataMapper") }) {
            val modelName =
                returnType?.name ?: return@filterAndAssertClassesFunctionsThatResideInPackage false
            containingFile.imports.find { it.name.contains(modelName) }
                ?: return@filterAndAssertClassesFunctionsThatResideInPackage false
            if (!modelName.endsWith("Model")) return@filterAndAssertClassesFunctionsThatResideInPackage false

            val expectedDtoNamePrefix = modelName.substringBefore("Model")
            val matchingDtoClass =
                containingFile.classes().find { it.name == "${expectedDtoNamePrefix}DTO" }

            return@filterAndAssertClassesFunctionsThatResideInPackage matchingDtoClass != null
        }
    }
}