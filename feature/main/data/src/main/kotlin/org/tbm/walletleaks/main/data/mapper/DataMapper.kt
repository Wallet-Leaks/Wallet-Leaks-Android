package org.tbm.walletleaks.main.data.mapper

interface DataMapper<T> {
    fun toDomain(): T
}