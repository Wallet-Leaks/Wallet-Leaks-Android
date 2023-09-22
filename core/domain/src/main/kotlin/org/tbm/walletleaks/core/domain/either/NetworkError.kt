package org.tbm.walletleaks.core.domain.either

sealed class NetworkError {

    class Unexpected(val error: String) : NetworkError()

    class Api(val error: String?) : NetworkError()

    class AuthApi(val errorResponse: String) : NetworkError()

    class ApiInputs(val error: MutableMap<String, List<String>>?) : NetworkError()

    object Timeout : NetworkError()
}