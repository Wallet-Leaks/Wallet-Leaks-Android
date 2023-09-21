package org.tbm.walletleaks.konsist.data.assertions.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.Test

internal class DataLayerArchitectureTest {

    @Test
    internal fun `ensure that data layer depends only on core and domain`() {
        val coreData = Layer("Core Data", "org.tbm.walletleaks.core.data..")
        Konsist.scopeFromProject()
            .assertArchitecture {
                listOf(
                    "org.tbm.walletleaks.authentication.data..",
                    "org.tbm.walletleaks.main.data.."
                ).forEach { definedBy ->
                    val data = Layer(definedBy, definedBy)
                    val domain =
                        Layer(
                            definedBy.replace("data", "domain"),
                            definedBy.replace("data", "domain")
                        )
                    data.dependsOn(coreData)
                    data.dependsOn(domain)
                }
            }
    }
}