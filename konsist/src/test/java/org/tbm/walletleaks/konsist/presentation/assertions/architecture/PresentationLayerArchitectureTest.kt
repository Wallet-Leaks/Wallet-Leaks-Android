package org.tbm.walletleaks.konsist.presentation.assertions.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.Test

internal class PresentationLayerArchitectureTest {
    @Test
    internal fun `ensure that presentation layer depends only on core and domain`() {
        val corePresentation = Layer("Core Presentation", "org.tbm.walletleaks.core.presentation..")
        Konsist.scopeFromProject()
            .assertArchitecture {
                listOf(
                    "org.tbm.walletleaks.authentication.presentation..",
                    "org.tbm.walletleaks.main.presentation.."
                ).forEach { definedBy ->
                    val presentation = Layer(definedBy, definedBy)
                    val domain =
                        Layer(
                            definedBy.replace("presentation", "domain"),
                            definedBy.replace("presentation", "domain")
                        )
                    presentation.dependsOn(corePresentation)
                    presentation.dependsOn(domain)
                }
            }
    }
}