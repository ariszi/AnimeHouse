package zisis.aristofanis.animehouse.core.domain

const val GENERIC_ERROR_MESSAGE= "Something went wrong"

object NetworkError : Result.Error()

data class MalformedContractError(val cause: Throwable) : Result.Error()

data class ApolloError(val message: String, val cause: Throwable?) : Result.Error()

data class UnknownError(val cause: Throwable?) : Result.Error()

