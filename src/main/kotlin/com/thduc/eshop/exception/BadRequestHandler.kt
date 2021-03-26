package com.thduc.eshop.exception

import org.springframework.validation.FieldError

import java.util.HashMap

import org.springframework.web.bind.MethodArgumentNotValidException

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.function.Consumer


class BadRequestHandler : RuntimeException() {
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String?> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return errors
    }
}