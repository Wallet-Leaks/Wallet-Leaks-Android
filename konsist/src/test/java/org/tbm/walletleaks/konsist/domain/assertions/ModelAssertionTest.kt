package org.tbm.walletleaks.konsist.domain.assertions

import org.junit.Test
import org.tbm.walletleaks.konsist.base.ClassAssertionTest

internal class ModelAssertionTest : ClassAssertionTest() {
    override val packageClassesResideIn = "..domain..models.."

    override val classSuffix = "Model"

    @Test
    internal fun `ensure that all classes with 'Model' suffix reside in 'models' package`() {
        assertClassesPackage()
    }

    @Test
    internal fun `ensure that all classes that reside in 'models' package have 'Model' suffix`() {
        assertClassesThatResideInPackage()
    }
}