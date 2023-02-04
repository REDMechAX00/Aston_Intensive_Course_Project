package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base

sealed class RemoteDetailsResult<Value : Any>(
    val status: ResultStatus,
    val data: Value?,
    val e: Throwable?
) {

    enum class ResultStatus {
        SUCCESS,
        ERROR
    }

    data class Error<Value : Any>(
        val throwable: Throwable
    ) : RemoteDetailsResult<Value>(ResultStatus.ERROR, null, throwable)

    data class Success<Value : Any>(
        val successData: Value?
    ) : RemoteDetailsResult<Value>(ResultStatus.SUCCESS, successData, null) {

        companion object {

            @Suppress("MemberVisibilityCanBePrivate")
            internal val EMPTY = Success(null)

            @Suppress("UNCHECKED_CAST")
            internal fun <Value : Any> empty() = EMPTY as Success<Value>
        }
    }
}