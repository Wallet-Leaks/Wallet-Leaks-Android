package org.tbm.walletleaks.core.data.base

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.tbm.walletleaks.core.domain.either.Either
import org.tbm.walletleaks.core.domain.either.NetworkError

abstract class RepositoryImpl {

    protected suspend inline fun <reified DTO : DTOMapper<Model>, reified Model : Any> HttpClient.makeGetNetworkRequest(
        crossinline requestBuilder: HttpRequestBuilder.() -> Unit
    ) =
        makeNetworkRequest<DTO, Model>(
            {
                get(DTO::class) {
                    requestBuilder()
                }
            }
        )

    protected inline fun <reified DTO : DTOMapper<Model>, reified Model : Any> HttpClient.makePostNetworkRequest(
        crossinline requestBuilder: HttpRequestBuilder.() -> Unit,
        crossinline transform: DTO.() -> DTO
    ) =
        makeNetworkRequest<DTO, Model>(
            {
                post(DTO::class) {
                    requestBuilder()
                }.body()
            }
        )

    protected inline fun <reified DTO : Any> HttpClient.makePostNetworkRequest(
        crossinline requestBuilder: HttpRequestBuilder.() -> Unit
    ) =
        makeNetworkRequest<DTO> {
            post(DTO::class) {
                requestBuilder()
            }
        }

    protected suspend inline fun <reified DTO : DTOMapper<Model>, reified Model : Any> HttpClient.makePutNetworkRequest(
        crossinline requestBuilder: HttpRequestBuilder.() -> Unit
    ) =
        makeNetworkRequest<DTO, Model>(
            {
                put(DTO::class) {
                    requestBuilder()
                }
            }
        )

    protected suspend inline fun <reified DTO : DTOMapper<Model>, reified Model : Any> HttpClient.makePatchNetworkRequest(
        crossinline requestBuilder: HttpRequestBuilder.() -> Unit
    ) =
        makeNetworkRequest<DTO, Model>(
            {
                patch(DTO::class) {
                    requestBuilder()
                }
            }
        )

    protected suspend inline fun <reified DTO : DTOMapper<Model>, reified Model : Any> HttpClient.makeDeleteNetworkRequest(
        crossinline requestBuilder: HttpRequestBuilder.() -> Unit
    ) =
        makeNetworkRequest<DTO, Model>(
            {
                delete(DTO::class) {
                    requestBuilder()
                }
            }
        )

    protected inline fun <reified T, reified S : Any> makeNetworkRequest(
        crossinline request: suspend () -> HttpResponse,
        crossinline transform: T.() -> S = { toDomain() }
    ): Flow<Either<NetworkError, S>> where T : Any, T : DTOMapper<S> {
        return flow {
            try {
                val response = request()
                when {
                    response.status.isSuccess() -> {
                        val body = response.body<T>()
                        emit(Either.Right(transform(body)))
                    }

                    response.status == HttpStatusCode.UnprocessableEntity -> {
                        emit(Either.Left(NetworkError.ApiInputs(response.body())))
                    }

                    else -> {
                        emit(Either.Left(NetworkError.Api(response.body())))
                    }
                }
            } catch (exception: TimeoutCancellationException) {
                emit(Either.Left(NetworkError.Timeout))
            } catch (exception: Exception) {
                val message = exception.localizedMessage ?: "Error Occurred!"
                emit(Either.Left(NetworkError.Unexpected(message)))
            }
        }
    }

    protected inline fun <reified T : Any> makeNetworkRequest(
        crossinline request: suspend () -> HttpResponse,
    ): Flow<Either<NetworkError, T>> {
        return flow {
            try {
                val response = request()
                when {
                    response.status.isSuccess() -> {
                        val body = response.body<T>()
                        emit(Either.Right(body))
                    }

                    response.status == HttpStatusCode.UnprocessableEntity -> {
                        emit(Either.Left(NetworkError.ApiInputs(response.body())))
                    }

                    else -> {
                        emit(Either.Left(NetworkError.Api(response.body())))
                    }
                }
            } catch (exception: TimeoutCancellationException) {
                emit(Either.Left(NetworkError.Timeout))
            } catch (exception: Exception) {
                val message = exception.localizedMessage ?: "Error Occurred!"
                emit(Either.Left(NetworkError.Unexpected(message)))
            }
        }
    }

//    protected suspend inline fun <reified T : Any, reified S : Any> makeNetworkRequest(
//        crossinline request: suspend () -> HttpResponse,
//        crossinline transform: T.() -> S
//    ): Flow<Either<NetworkError, S>> {
//        return flow {
//            try {
//                val response = request()
//                when {
//                    response.status.isSuccess() -> {
//                        val body = response.body<T>()
//                        emit(Either.Right(transform(body)))
//                    }
//
//                    response.status == HttpStatusCode.UnprocessableEntity -> {
//                        emit(Either.Left(NetworkError.ApiInputs(response.body())))
//                    }
//
//                    else -> {
//                        emit(Either.Left(NetworkError.Api(response.body())))
//                    }
//                }
//            } catch (exception: TimeoutCancellationException) {
//                emit(Either.Left(NetworkError.Timeout))
//            } catch (exception: Exception) {
//                val message = exception.localizedMessage ?: "Error Occurred!"
//                emit(Either.Left(NetworkError.Unexpected(message)))
//            }
//        }
//    }
}