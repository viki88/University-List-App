package com.vikination.universitylistapp.data.source.network

import com.vikination.universitylistapp.data.source.network.ConstNetwork.CLIENT_ERROR
import com.vikination.universitylistapp.data.source.network.ConstNetwork.HTTP_UNKNOWN_ERROR
import com.vikination.universitylistapp.data.source.network.ConstNetwork.NETWORK_ERROR
import com.vikination.universitylistapp.data.source.network.ConstNetwork.SERVER_ERROR
import com.vikination.universitylistapp.data.source.network.ConstNetwork.UNKNOWN_ERROR
import okio.IOException
import retrofit2.HttpException

suspend fun <T> asResponseResourceSuspend(apiCall: suspend () -> T): ResponseResource<T> {
    return try {
        ResponseResource.Loading(true)
        val response = apiCall.invoke()
        ResponseResource.Success(response)
    } catch (error: Throwable) {
        val exception = when (error) {
            is HttpException -> {
                when (error.code()) {
                    in 400..499 -> {
                        ClientException(
                            message = "${CLIENT_ERROR}: ${error.code()}",
                            cause = error,
                        )
                    }

                    in 500..599 -> ServerException(
                        message = "${SERVER_ERROR}: ${error.code()}",
                        cause = error
                    )

                    else -> UnknownException(
                        message = "${HTTP_UNKNOWN_ERROR}: ${error.code()}",
                        cause = error
                    )
                }
            }

            is IOException -> NetworkException(
                message = NETWORK_ERROR,
                cause = error
            )

            else -> AppException(
                message = UNKNOWN_ERROR,
                cause = error
            )
        }

        val errorCode = when (error) {
            is HttpException -> {
                when (error.code()) {
                    in 400..499 -> {
                        "#ER${error.code()}"
                    }

                    in 500..599 -> {
                        "#ER${error.code()}"
                    }

                    else -> {
                        "#ER${error.code()}"
                    }
                }
            }

            else -> {
                error.cause?.message.toString()
            }
        }
        ResponseResource.Error(exception, errorCode)
    } finally {
        ResponseResource.Loading(false)
    }
}

sealed interface ResponseResource<out T> {
    data class Success<T>(val data: T) : ResponseResource<T>
    data class Error(val exception: AppException, val errorCode: String? = null) :
        ResponseResource<Nothing>

    data class Loading(val status: Boolean) : ResponseResource<Nothing>
}

open class AppException(message: String? = null, cause: Throwable? = null) :
    Throwable(message, cause)

class NetworkException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ServerException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ClientException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class UnknownException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)