package com.up42.codingchallenge.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class FeatureFileReadException : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(
        message: String?,
        throwable: Throwable?
    ) : super(throwable?.let {
        String.format("{\"originalMessage\" : \"%s\" , \"customMessage\" : %s}", it.message, message)
    }) {
        if (throwable != null) {
            stackTrace = throwable.stackTrace
        }
    }

}