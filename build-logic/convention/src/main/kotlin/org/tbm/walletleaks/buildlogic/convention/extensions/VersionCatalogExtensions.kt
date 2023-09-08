package org.tbm.walletleaks.buildlogic.convention.extensions

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import java.util.Optional

internal fun <T> Optional<Provider<T>>.extractValue() =
    extractType().extractType()

private fun <T> Optional<T>.extractType(): T = get()

private fun <T> Optional<T>.extractPrimitive(): Any {
    return when (val value = get()) {
        is Boolean ->
            value.toString().toBoolean()

        is Byte ->
            value.toByte()

        is Char ->
            value.toChar()

        is Double ->
            value.toDouble()

        is Float ->
            value.toFloat()

        is Int ->
            value.toInt()

        is Long ->
            value.toLong()

        is Short ->
            value.toShort()

        else ->
            value.toString()
    }
}

private fun <T> Provider<T>.extractType(): T = get()

private fun <T> Provider<T>.extractPrimitive(): Any {
    return when (val value = get()) {
        is Boolean ->
            value.toString().toBoolean()

        is Byte ->
            value.toByte()

        is Char ->
            value.toChar()

        is Double ->
            value.toDouble()

        is Float ->
            value.toFloat()

        is Int ->
            value.toInt()

        is Long ->
            value.toLong()

        is Short ->
            value.toShort()

        else ->
            value.toString()
    }
}

internal fun VersionCatalog.version(alias: String) = findVersion(alias).get()

internal fun VersionCatalog.library(alias: String) = findLibrary(alias).get()

internal fun VersionCatalog.bundle(alias: String) = findBundle(alias).get()

internal fun VersionCatalog.plugin(alias: String) = findPlugin(alias).extractValue()