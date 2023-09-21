package org.tbm.walletleaks.konsist.domain.assertions.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.Test

internal class DomainLayerArchitectureTest {

    @Test
    internal fun `ensure that domain layer is independent`() {
        val coreDomain =
            Layer("Core Domain", "org.tbm.walletleaks.core.domain..")
        Konsist.scopeFromProject()
            .assertArchitecture {
                listOf(
                    "org.tbm.walletleaks.authentication.domain..",
                    "org.tbm.walletleaks.main.domain.."
                ).forEach { definedBy ->
                    val domain = Layer(definedBy, definedBy)
                    domain.dependsOn(coreDomain)
                }
            }
    }
}