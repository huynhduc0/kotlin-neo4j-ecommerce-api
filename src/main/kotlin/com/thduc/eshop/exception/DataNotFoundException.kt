package com.thduc.eshop.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class DataNotFoundException : RuntimeException {
    constructor() : super() {}
    constructor(
        resourceName: String?,
        fieldName: String?,
        fieldValue: String?
    ) : super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)) {
    }
}
