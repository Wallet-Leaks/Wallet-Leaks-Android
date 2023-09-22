package org.tbm.walletleaks.core.data.base

interface DTOMapper<T> {
    fun toDomain(): T
}