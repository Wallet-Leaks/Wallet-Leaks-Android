package com.timberta.walletleaks.data.utils

interface DataMapper<T> {
    fun toDomain(): T
}