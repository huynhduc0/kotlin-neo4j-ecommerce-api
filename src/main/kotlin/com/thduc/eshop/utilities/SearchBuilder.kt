package com.thduc.eshop.utilities

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.StringPath

import com.querydsl.core.types.dsl.NumberPath

import com.querydsl.core.types.dsl.PathBuilder

import com.querydsl.core.types.dsl.BooleanExpression
import com.thduc.eshop.entity.Product
import org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric
import com.querydsl.core.types.dsl.Expressions
import java.util.*

import java.util.stream.Collectors

import java.util.function.Function


class SearchBuilder(
    private var params: MutableList<SearchCriteria>? = null,
    private val clazz: Class<*>
) {


    fun with(
        key: String?, operation: String?, value: Any?
    ): SearchBuilder? {
        params!!.add(SearchCriteria(key, operation, value))
        return this
    }

    fun build(): BooleanExpression? {
        if (params!!.size == 0) {
            return null
        }
        val predicates: List<*> = params!!.stream().map(Function<SearchCriteria, Any> { param: SearchCriteria? ->
            val predicate = CustomPredicate(param)
            predicate.predicate(clazz)
        }).filter(Objects::nonNull).collect(Collectors.toList())
        var result = Expressions.asBoolean(true).isTrue
        for (predicate in predicates) {
            result = result.and(predicate as Predicate?)
        }
        return result
    }

}