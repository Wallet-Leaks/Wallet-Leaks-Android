package org.tbm.walletleaks.konsist.data.assertions

import org.junit.Test
import org.tbm.walletleaks.konsist.base.ClassAssertionTest

internal class RepositoryImplAssertionTest : ClassAssertionTest() {
    override val packageClassesResideIn = "..data..repositories.."

    override val classSuffix = "RepositoryImpl"

    @Test
    internal fun `ensure that all classes with 'RepositoryImpl' suffix reside in 'repositories' package`() {
        assertClassesPackage()
    }

    @Test
    fun `ensure that all classes that reside in 'repositories' package have 'RepositoryImpl' suffix`() {
        assertClassesThatResideInPackage()
    }

    @Test
    internal fun `classes with 'RepositoryImpl' should implement their corresponding interface`() {
        assertClassesThatResideInPackage {
            hasParents(name.removeSuffix("Impl"))
        }
    }
}