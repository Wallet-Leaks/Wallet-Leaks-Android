package org.tbm.walletleaks.main.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.tbm.walletleaks.main.data.mapper.DataMapper
import org.tbm.walletleaks.main.domain.models.ChelModel

@Serializable
data class ChelDTO(
    @SerialName("daun") val name: String
) : DataMapper<ChelModel> {
    override fun toDomain(): ChelModel =
        ChelModel(name)
}